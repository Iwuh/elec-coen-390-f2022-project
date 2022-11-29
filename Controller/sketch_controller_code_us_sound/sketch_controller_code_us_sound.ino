#include "EventArray.h"
#include "WifiHelper.h"

#include <Firebase_ESP_Client.h>
#include "addons/TokenHelper.h"
#include "addons/RTDBHelper.h"

#include "stdlib_noniso.h"

// It's okay for these to be publicly exposed; the RTDB will later be protected by security rules with email/password authentication.
constexpr const char* FIREBASE_DATABASE_URL = "https://fir-b9c10-default-rtdb.firebaseio.com";
constexpr const char* FIREBASE_API_KEY = "AIzaSyAjsikI9TXU2kSRZweVa8uqgLyiEPOTplg";

// Globals for Firebase connection
FirebaseData rtdb;
FirebaseAuth auth;
FirebaseConfig config;
bool signupOK = false;

// Pin assignments for the ultrasonic sensors
int trigPin1 = D3;
int echoPin1 = D4;
int trigPin2 = D5;
int echoPin2 = D6;

// Pin assignment for sound sensor envelope gate
const int ENVELOPE_PIN = A0;

// Globals for tracking occupancy events
constexpr double SENSOR_LOW_TRIGGER_THRESHOLD = 0.9; // Sensor will register person passing by if distance measures less than this fraction of the calibrated value.
double sensorLowThreshold1;
double sensorLowThreshold2;

// 343 m/s * 100 cm/m * 1s/1000000us
constexpr double US_TO_CM = 0.0343;

// Used in ultrasonic sensor FSM to prevent duplicate detections
constexpr int STATE_UNDETECTED = 0;
constexpr int STATE_DETECTED = 1;
int sensorPrevState1;
int sensorPrevState2;

//Variables to make the rolling average calculation of the sound sensor envelope gate gathered data set
//Primarly set to size of 10 data points assessed in each iteration
int AmplitudeArray[10];
int arrayCount = 0;
int total = 0;
float rolling_average = 0;

// Track count of people entering or leaving
int totalCount = 0;

// Interval of time between updates of Firebase, in microseconds
constexpr unsigned long UPDATE_INTERVAL_MICROS = 15 * 1000 * 1000;
unsigned long lastUpdate_sound = 0;
unsigned long lastUpdate_occ = 0;

// Holds triggers of the ultrasonic sensors to match occupancy changes
EventArray sensorEvents_1;
EventArray sensorEvents_2;

// Find a sensor's default value and store the low trigger threshold in calibrationResult
void calibrateSensor(int &trigPin, int &echoPin, double *calibrationResult) {
  // Get 5 readings from the sensor
  unsigned long readings[5];
  for (int i = 0; i < 5; i++)
  {
    digitalWrite(trigPin, HIGH);
    delayMicroseconds(10);
    digitalWrite(trigPin, LOW);    
    readings[i] = pulseIn(echoPin, HIGH);
    delay(100);
  }

  // Sort the readings in place with insertion sort
  for (int i = 1; i < 5; i++)
  {
    for (int j = i; j > 0; j--)
    {
      if (readings[j] < readings[j-1])
      {
        unsigned long temp = readings[j];
        readings[j] = readings[j-1];
        readings[j-1] = temp;
      }
    }
  }
  // Get the median, multiply it by the low fraction, and store it in the desired location.
  *calibrationResult = readings[2] / 2 * US_TO_CM * SENSOR_LOW_TRIGGER_THRESHOLD;
}

void setup() {
  // Setup US sensor pins
  pinMode(trigPin1, OUTPUT);
  pinMode(echoPin1, INPUT);
  pinMode(trigPin2, OUTPUT);
  pinMode(echoPin2, INPUT);

  // Setup sound sensor envelope pin
  pinMode(ENVELOPE_PIN, INPUT);

  Serial.begin(9600);

  // Find the trigger values for each US sensor to detect a person.
  calibrateSensor(trigPin1, echoPin1, &sensorLowThreshold1);
  Serial.print("Sensor 1 threshold: ");
  Serial.println(sensorLowThreshold1);
  calibrateSensor(trigPin2, echoPin2, &sensorLowThreshold2);
  Serial.print("Sensor 2 threshold: ");
  Serial.println(sensorLowThreshold2);

  sensorPrevState1 = STATE_UNDETECTED;
  sensorPrevState2 = STATE_UNDETECTED;

  // Initialize Wifi and start SNTP sync
  WifiHelper helper;
  helper.ConnectToHomeNetwork();
  helper.StartTimeZoneSynchronization();

  // Connect to Firebase
  config.database_url = FIREBASE_DATABASE_URL;
  config.api_key = FIREBASE_API_KEY;

  Firebase.reconnectWiFi(true);

  // TODO: Once email authentication is set up, use that here.
  if (Firebase.signUp(&config, &auth, FIREBASE_USERNAME, FIREBASE_PASSWORD)) {
    Serial.println("Firebase signup OK");
    signupOK = true;    
  } else {
    Serial.println("Firebase signup failed");
    Serial.println(config.signer.signupError.message.c_str());
  }

  config.token_status_callback = tokenStatusCallback;
  Firebase.begin(&config, &auth);
}

void loop() {
  // Sound Sensor rolling average implementation for assessing sound level - done first
  
  // Getting analog value reading from the envelope pin that traces the amplitude of the sound gathered by the sensor
  int analog_value = analogRead(ENVELOPE_PIN);
  //delay(50);

  // Storing those analog values in the array of amplitudes on each iteration of the main loop 
  AmplitudeArray[arrayCount++] = analog_value;

  // If chosen number of datasets per rolling average is reached, calculation of that rolling average is done and prepared to be sent to firebase
  if(arrayCount == 10){
    total = 0;
    for(int i = 0; i < arrayCount; i++){   // Computing total sum of all datasets stored in the array of amplitudes
      total += AmplitudeArray[i];
    } 

    // Simple average calcualtion based on previously calculated total
    rolling_average = total/(double(arrayCount));
    Serial.println(rolling_average);    

    // Resetting count for next iteration
    arrayCount = 0;
  }

  // Upload the current rolling average of last 10 datapoints gathered from the sound sensor to Firebase, if enough time has elapsed since the last sound sensor update.
  unsigned long now_sound_end = micros();

  if (signupOK && Firebase.ready() && now_sound_end > lastUpdate_sound + UPDATE_INTERVAL_MICROS) {
    Firebase.RTDB.setFloatAsync(&rtdb, "/Sensors/Noise_sensor1/floatAvg", rolling_average);
    Serial.println("Updated Firebase with latest Sound Sensor rolling average");
    lastUpdate_sound = now_sound_end;
  }
  
  delay(10);
  
  // US sensors occupancy algorithm
  // Get a reading from US sensor 1.
  digitalWrite(trigPin1, HIGH);
  delayMicroseconds(10);
  digitalWrite(trigPin1, LOW);

  unsigned long sensor1Pulse = pulseIn(echoPin1, HIGH);
  double sensor1Distance = sensor1Pulse / 2 * US_TO_CM;

  // Only log a sensor event when the state changes from "obstacle" back to "no obstacle".
  // This prevents duplicate events from a person standing in front of the sensor.
  if (sensorPrevState1 == STATE_UNDETECTED && sensor1Distance <= sensorLowThreshold1) {
    Serial.println("Sensor 1 changed to detected");
    sensorPrevState1 = STATE_DETECTED;
  } else if (sensorPrevState1 == STATE_DETECTED && sensor1Distance > sensorLowThreshold1) {
    Serial.print("Sensor 1 detected: ");
    Serial.println(sensor1Distance);
    unsigned long now = micros();
    sensorPrevState1 = STATE_UNDETECTED;
    unsigned long match = sensorEvents_2.findAndRemove(now);
    if (match == -1) {
      // no match found
      sensorEvents_1.add(now);
    } else {
      // match found, this means a person has left
      totalCount--;
      Serial.println("A person has left.");
    }
  } else if (sensorPrevState1 != STATE_DETECTED && sensorPrevState1 != STATE_UNDETECTED) {
    // This should never happen, but if it does, then reset to undetected.
    Serial.print("Reset sensor 1 state, incorrect state was ");
    Serial.println(sensorPrevState1);
    sensorPrevState1 = STATE_UNDETECTED;
  }

  // The HC-SR04 datasheet recommends a minimum 60ms polling cycle.
  // We stagger the two sensors' polling cycles by 30ms to avoid crosstalk.
  delay(30);  

  // Repeat the process with the second US sensor.
  digitalWrite(trigPin2, HIGH);
  delayMicroseconds(10);
  digitalWrite(trigPin2, LOW);

  unsigned long sensor2Pulse = pulseIn(echoPin2, HIGH);
  double sensor2Distance = sensor2Pulse / 2 * US_TO_CM;

  if (sensorPrevState2 == STATE_UNDETECTED && sensor2Distance <= sensorLowThreshold2) {
    Serial.println("Sensor 2 changed to detected");
    sensorPrevState2 = STATE_DETECTED;
  } else if (sensorPrevState2 == STATE_DETECTED && sensor2Distance > sensorLowThreshold2) {
    Serial.print("Sensor 2 detected: ");
    Serial.println(sensor2Distance);
    unsigned long now = micros();    
    sensorPrevState2 = STATE_UNDETECTED;
    unsigned long match = sensorEvents_1.findAndRemove(now);
    if (match == -1) {
      // no match found
      sensorEvents_2.add(now);
    } else {
      // match found, this means a person has entered
      totalCount++;
      Serial.println("A person has entered.");
    }
  } else if (sensorPrevState2 != STATE_DETECTED && sensorPrevState2 != STATE_UNDETECTED) {
    // This should never happen, but if it does, then reset to undetected.
    Serial.print("Reset sensor 2 state, incorrect state was ");
    Serial.println(sensorPrevState2);
    sensorPrevState2 = STATE_UNDETECTED;
  }

  // Clean up any old sensor events.
  unsigned long now_occ_end = micros();
  sensorEvents_1.clean(now_occ_end);
  sensorEvents_2.clean(now_occ_end);

  // Upload the current occupancy count to Firebase, if enough time has elapsed since the last occupancy count update.
  if (signupOK && Firebase.ready() && now_occ_end > lastUpdate_occ + UPDATE_INTERVAL_MICROS) {    
    char buf[10]; // 9 chars + null terminator means a max value of 999999999. This should be fine...
    itoa(totalCount, buf, 10); // The 10 is not the number of characters, it's the base to represent the number in.
    Firebase.RTDB.setStringAsync(&rtdb, "/Sensors/Occ_sensor1/strMeasurement", buf);
    Serial.println("Updated Firebase");
    lastUpdate_occ = now_occ_end;
  }
  
  delay(30);

}