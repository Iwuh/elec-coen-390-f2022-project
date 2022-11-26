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

// Globals for tracking occupancy events
constexpr double SENSOR_LOW_TRIGGER_THRESHOLD = 0.9; // Sensor will register person passing by if distance measures less than this fraction of the calibrated value.
double sensorLowThreshold1;
double sensorLowThreshold2;

// 343 m/s * 100 cm/m * 1s/1000000us
constexpr double US_TO_CM = 0.0343;
volatile unsigned long pulseStart1;
volatile unsigned long pulseStart2;

constexpr int STATE_UNDETECTED = 0;
constexpr int STATE_DETECTED = 1;
int sensorPrevState1 = STATE_UNDETECTED;
int sensorPrevState2 = STATE_UNDETECTED;

int totalCount = 0;

constexpr unsigned long UPDATE_INTERVAL_MICROS = 15 * 1000 * 1000;
unsigned long lastUpdate = 0;

EventArray sensorEvents_1;
EventArray sensorEvents_2;

/*void IRAM_ATTR echo1_ISR() {
  int state = digitalRead(echoPin1);
  if (state == HIGH) {
    pulseStart1 = micros();
  } else if (state == LOW) {
    unsigned long pulseLength = micros() - pulseStart1;
    double distance = pulseLength / 2 * US_TO_CM;
    if (distance <= sensorLowThreshold1 && sensorPrevState1 == STATE_UNDETECTED) {
      sensorPrevState1 = STATE_DETECTED;
    }
  }
}*/

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

  pinMode(trigPin1, OUTPUT);
  pinMode(echoPin1, INPUT);
  pinMode(trigPin2, OUTPUT);
  pinMode(echoPin2, INPUT);

  Serial.begin(9600);

  calibrateSensor(trigPin1, echoPin1, &sensorLowThreshold1);
  Serial.print("Sensor 1 threshold: ");
  Serial.println(sensorLowThreshold1);
  calibrateSensor(trigPin2, echoPin2, &sensorLowThreshold2);
  Serial.print("Sensor 2 threshold: ");
  Serial.println(sensorLowThreshold2);

  WifiHelper helper;
  helper.ConnectToHomeNetwork();
  helper.StartTimeZoneSynchronization();

  config.database_url = FIREBASE_DATABASE_URL;
  config.api_key = FIREBASE_API_KEY;

  Firebase.reconnectWiFi(true);

  // TODO: Once email authentication is set up, use that here.
  if (Firebase.signUp(&config, &auth, "", "")) {
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
  digitalWrite(trigPin1, HIGH);
  delayMicroseconds(10);
  digitalWrite(trigPin1, LOW);

  unsigned long sensor1Pulse = pulseIn(echoPin1, HIGH);
  double sensor1Distance = sensor1Pulse / 2 * US_TO_CM;
  
  // No state
  /*if (sensor1Distance <= sensorLowThreshold1)
  {
    Serial.print("Sensor 1 detected: ");
    Serial.println(sensor1Distance);
    unsigned long now = micros();
    sensorPrevState1 = STATE_DETECTED;
    unsigned long match = sensorEvents_2.findAndRemove(now);
    if (match == -1) {
      // no match found
      sensorEvents_1.add(now);
    } else {
      // match found, this means a person has left
      totalCount--;
      Serial.println("A person has left.");
    }
  }*/

  // Trigger on change from detected to undetected
  if (sensorPrevState1 == STATE_UNDETECTED && sensor1Distance <= sensorLowThreshold1) {
    Serial.println("Sensor 1 changed to detected");
    sensorPrevState1 == STATE_DETECTED;
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
    sensorPrevState1 == STATE_UNDETECTED;
  }

  // Trigger on change from undetected to detected
  /*if (sensor1Distance <= sensorLowThreshold1 && sensorPrevState1 == STATE_UNDETECTED) {
    // If there is someone in the way who wasn't there before
    unsigned long now = micros();
    sensorPrevState1 = STATE_DETECTED;
    unsigned long match = sensorEvents_2.findAndRemove(now);
    if (match == -1) {
      // no match found
      sensorEvents_1.add(now);
    } else {
      // match found, this means a person has left
      totalCount--;
      Serial.println("A person has left.");
    }
  } else if (sensor1Distance > sensorLowThreshold1 && sensorPrevState1 == STATE_DETECTED) {
    // If there was someone in the way, but they moved
    sensorPrevState1 == STATE_UNDETECTED;    
  }*/

  // The HC-SR04 datasheet recommends a minimum 60ms polling cycle.
  // We stagger the two sensors' polling cycles by 30ms to avoid crosstalk.
  delay(30);  

  digitalWrite(trigPin2, HIGH);
  delayMicroseconds(10);
  digitalWrite(trigPin2, LOW);

  unsigned long sensor2Pulse = pulseIn(echoPin2, HIGH);
  double sensor2Distance = sensor2Pulse / 2 * US_TO_CM;

  /*if (sensor2Distance <= sensorLowThreshold2)
  {
    Serial.print("Sensor 2 detected: ");
    Serial.println(sensor2Distance);
    unsigned long now = micros();    
    sensorPrevState2 = STATE_DETECTED;
    unsigned long match = sensorEvents_1.findAndRemove(now);
    if (match == -1) {
      // no match found
      sensorEvents_2.add(now);
    } else {
      // match found, this means a person has entered
      totalCount++;
      Serial.println("A person has entered.");
    }
  }*/

  if (sensorPrevState2 == STATE_UNDETECTED && sensor2Distance <= sensorLowThreshold2) {
    Serial.println("Sensor 2 changed to detected");
    sensorPrevState2 == STATE_DETECTED;
  } else if (sensorPrevState2 == STATE_DETECTED && sensor2Distance > sensorLowThreshold2) {
    Serial.print("Sensor 2 detected: ");
    Serial.println(sensor2Distance);
    unsigned long now = micros();    
    sensorPrevState2 = STATE_DETECTED;
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
    Serial.println(sensorPrevState1);
    sensorPrevState2 == STATE_UNDETECTED;
  }

  /*if (sensor2Distance <= sensorLowThreshold2 && sensorPrevState2 == STATE_UNDETECTED) {
    // If there is someone in the way who wasn't there before
    unsigned long now = micros();    
    sensorPrevState2 = STATE_DETECTED;
    unsigned long match = sensorEvents_1.findAndRemove(now);
    if (match == -1) {
      // no match found
      sensorEvents_2.add(now);
    } else {
      // match found, this means a person has left
      totalCount--;
      Serial.println("A person has left.");
    }
  } else if (sensor2Distance > sensorLowThreshold2 && sensorPrevState2 == STATE_DETECTED) {
    // If there was someone in the way, but they moved
    sensorPrevState1 == STATE_UNDETECTED;    
  }*/

  unsigned long now = micros();
  sensorEvents_1.clean(now);
  sensorEvents_2.clean(now);

  if (signupOK && Firebase.ready() && now > lastUpdate + UPDATE_INTERVAL_MICROS) {
    char buf[10];
    itoa(totalCount, buf, 10);
    Firebase.RTDB.setStringAsync(&rtdb, "/Sensors/Occ_sensor1/strMeasurement", buf);
    Serial.println("Updated Firebase");
    lastUpdate = now;
  }
  
  delay(30);

}