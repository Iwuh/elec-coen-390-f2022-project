#include "EventArray.h"

int ledPin1 = 5;  // choose the pins for the LEDs
//int ledPin2 = 15;
int sensorPin1 = 27;
int sensorPin2 = 26;
int pirState1 = LOW;  // assuming first no motion detected
int pirState2 = LOW;

int totalCount = 0;
int currentCount = 0;

EventArray sensorEvents_1;
EventArray sensorEvents_2;

// Return the system timestamp in microseconds.
int64_t getTimeMicros() {
  struct timeval tv_now;
  gettimeofday(&tv_now, NULL);
  return (int64_t)tv_now.tv_sec * 1000000L + (int64_t)tv_now.tv_usec;
}

// Polling
/*
void setup() {
  pinMode(sensorPin1, INPUT_PULLUP);  // declare motion sensors as input
  pinMode(sensorPin2, INPUT_PULLUP);
  pinMode(ledPin1, OUTPUT);  // declare LED1 as output, the built in ESP32 chip led
  //pinMode(ledPin2, OUTPUT);            // currently not functional due to lack of applicable LEDs
  Serial.begin(9600);
}

void loop() {

  unsigned long currentMillisSensor1 = 0;  //  variables to store time when sensor state change happened
  unsigned long currentMillisSensor2 = 0;
  unsigned long previousMillisSensor1 = 0;
  unsigned long previousMillisSensor2 = 0;

  //Sensor 1
  if (digitalRead(sensorPin1) == HIGH) {  // check if input1 is HIGH
    digitalWrite(ledPin1, HIGH);          // turn LED1 ON in that case
    if (pirState1 == LOW) {
      // have just turned on
      Serial.println("Sensor 1: Motion detected!");
      // only want to print on the output change, not state
      pirState1 = HIGH;
      int64_t now = getTimeMicros();
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
      currentMillisSensor1 = millis();
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
      int64_t now = getTimeMicros();
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

      currentMillisSensor2 = millis();  // timestamp of Sensor2 state change saved
    }
  }

  //Millis() approach

  //  comparing timestamps of sensor state change between the two sensors
  //  if Sensor1 state changed earlier than Sensor2 state => person entering the door
  //  otherwise => person leaving the door

  //if ((previousMillisSensor1 != currentMillisSensor1) && (previousMillisSensor2 != currentMillisSensor2)) {  // update number of person entering or leaving only if timestamps of state changes for both sensors change
    //if (currentMillisSensor1 > currentMillisSensor2) {
      //totalCount++;
      //Serial.println("A person just entered.");
    //} else {
     // totalCount--;
    //  Serial.println("A person just left.");
   // }
    Serial.print("Current Total: ");
    Serial.println(totalCount);
    delay(50);
  }

  previousMillisSensor1 = currentMillisSensor1;
  previousMillisSensor2 = currentMillisSensor2;
}
*/


//Tests and extra Examples

//Example using interrupts

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
}


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