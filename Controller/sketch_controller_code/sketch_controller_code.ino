#include "EventArray.h"
#include "WifiHelper.h"

#include <Firebase_ESP_Client.h>
#include "addons/TokenHelper.h"
#include "addons/RTDBHelper.h"

#include "stdlib_noniso.h"

// It's okay for these to be publicly exposed; the RTDB will later be protected by security rules with email/password authentication.
constexpr const char* FIREBASE_DATABASE_URL = "https://fir-b9c10-default-rtdb.firebaseio.com/";
constexpr const char* FIREBASE_API_KEY = "AIzaSyAjsikI9TXU2kSRZweVa8uqgLyiEPOTplg";

FirebaseData rtdb;
FirebaseAuth auth;
FirebaseConfig config;
bool signupOK;

int ledPin1 = 5;  // choose the pins for the LEDs
//int ledPin2 = 15;
int sensorPin1 = 27;
int sensorPin2 = 26;
int pirState1 = LOW;  // assuming first no motion detected
int pirState2 = LOW;

int totalCount = 0;
int currentCount = 0;

constexpr int64_t UPDATE_INTERVAL_MICROS = 60 * 1000 * 1000;
int64_t lastUpdate = 0;
int64_t now;

EventArray sensorEvents_1;
EventArray sensorEvents_2;

// Return the system timestamp in microseconds.
int64_t getTimeMicros() {
  struct timeval tv_now;
  gettimeofday(&tv_now, NULL);
  return (int64_t)tv_now.tv_sec * 1000000L + (int64_t)tv_now.tv_usec;
}

// Polling

void setup() {
  pinMode(sensorPin1, INPUT_PULLUP);  // declare motion sensors as input
  pinMode(sensorPin2, INPUT_PULLUP);
  pinMode(ledPin1, OUTPUT);  // declare LED1 as output, the built in ESP32 chip led
  //pinMode(ledPin2, OUTPUT);            // currently not functional due to lack of applicable LEDs
  Serial.begin(9600);

  WifiHelper helper;
  helper.ConnectToHomeNetwork();
  helper.StartTimeZoneSynchronization();

  config.database_url = FIREBASE_DATABASE_URL;
  config.api_key = FIREBASE_API_KEY;

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
  now = getTimeMicros();
  //Sensor 1
  if (digitalRead(sensorPin1) == HIGH) {  // check if input1 is HIGH
    digitalWrite(ledPin1, HIGH);          // turn LED1 ON in that case
    if (pirState1 == LOW) {
      // have just turned on
      Serial.println("Sensor 1: Motion detected!");
      // only want to print on the output change, not state
      pirState1 = HIGH;
      int64_t match = sensorEvents_2.findAndRemove(now);
      if (match == -1) {
        // no match found
        sensorEvents_1.add(now);
      } else {
        // match found, this means a person has left
        totalCount--;
        Serial.println("A person has left.");
      }
    }
  } else {
    digitalWrite(ledPin1, LOW);  // turn LED1 OFF
    if (pirState1 == HIGH) {
      // have just turned off
      Serial.println("Sensor 1: Motion ended!");
      // only want to print on the output change, not state
      pirState1 = LOW;
    }
  }

  //Sensor 2
  if (digitalRead(sensorPin2) == HIGH) {  // check if input1 is HIGH
    digitalWrite(ledPin1, HIGH);          // turn LED1 ON in that case
    //digitalWrite(ledPin2, HIGH);  // turn LED2 ON, currently not functional due to lack of applicable LEDs
    if (pirState2 == LOW) {
      // have just turned on
      Serial.println("Sensor 2: Motion detected!");
      // only want to print on the output change, not state
      pirState2 = HIGH;
      int64_t match = sensorEvents_1.findAndRemove(now);
      if (match == -1) {
        // no match found
        sensorEvents_2.add(now);
      } else {
        // match found, this means a person has entered
        totalCount++;
        Serial.println("A person has entered.");
      }
    }
  } else {
    digitalWrite(ledPin1, LOW);  //  turn LED1 OFF
    //digitalWrite(ledPin2, LOW); // turn LED2 OFF, currently not functional due to lack of applicable LEDs
    if (pirState2 == HIGH) {
      // have just turned off
      Serial.println("Sensor 2: Motion ended!");
      // only want to print on the output change, not state
      pirState2 = LOW;
    }
  }

  sensorEvents_1.clean(now);
  sensorEvents_2.clean(now);

  if (signupOK && Firebase.ready() && now > lastUpdate + UPDATE_INTERVAL_MICROS) {
    char buf[10];
    itoa(totalCount, buf, 10);
    Firebase.RTDB.setStringAsync(&rtdb, "/Sensors/Occ_sensor1/strMeasurement", buf);
    Serial.println("Updated Firebase");
    lastUpdate = now;
  }
  
  delay(50);

}



//Tests and extra Examples

//Example using interrupts
/*
void IRAM_ATTR sensor1_ISR() {
  int64_t now = getTimeMicros();
  int64_t match = sensorEvents_2.findAndRemove(now);
  if (match == -1) {
    // no match found
    sensorEvents_1.add(now);
  } else {
    // match found, this means a person has left
    totalCount--;
  }  
}

void IRAM_ATTR sensor2_ISR() {
  int64_t now = getTimeMicros();
  int64_t match = sensorEvents_1.findAndRemove(now);
  if (match == -1) {
    // no match found
    sensorEvents_2.add(now);
  } else {
    // match found, this means a person has entered
    totalCount++;
  }  
}

void setup() {
  // Serial port for debugging purposes
  Serial.begin(9600);
  
  // PIR Motion Sensor mode INPUT_PULLUP
  pinMode(sensorPin1, INPUT_PULLUP);  
  pinMode(sensorPin2, INPUT_PULLUP);
  // Set motionSensor pin as interrupt, assign interrupt function and set RISING mode
  attachInterrupt(digitalPinToInterrupt(sensorPin1), sensor1_ISR, RISING);
  attachInterrupt(digitalPinToInterrupt(sensorPin2), sensor2_ISR, RISING);

  // Set LED to LOW
  pinMode(ledPin, OUTPUT);
  digitalWrite(ledPin, LOW);
}

void loop() {
  now = getTimeMicros();
  Serial.print("Time: ");
  Serial.print(now);
  Serial.print(", Total count: ");
  Serial.println(totalCount);
  delay(1000);
}
*/

/*
//Test - blinking LED
void setup()  {
  pinMode(27,OUTPUT);
  Serial.begin(9600);
}
void loop()  {
  digitalWrite(27,HIGH);
  delay(500);
  digitalWrite(27,LOW);
  delay(500);
}
*/

/*
//Test - simple, no LEDs
int sensorPin1 =26;                
int sensorPin2 = 27;

void setup()  {
  Serial.begin(9600);
  pinMode(sensorPin1,INPUT);
  pinMode(sensorPin2,INPUT);
  digitalWrite(sensorPin1,LOW);
  digitalWrite(sensorPin2,LOW);
}
void loop()  {
    //Sensor 1
    if(digitalRead(sensorPin1)==HIGH)  {
      Serial.println("Sensor 1: Motion detected!");
    }
    else  {
      Serial.println("Sensor 1: No motion!");
    }
    //Sensor 2
    if(digitalRead(sensorPin2)==HIGH)  {
      Serial.println("Sensor 2: Motion detected!");
    }
    else  {
      Serial.println("Sensor 2: No motion!");
    }
    delay(1000);
}
*/