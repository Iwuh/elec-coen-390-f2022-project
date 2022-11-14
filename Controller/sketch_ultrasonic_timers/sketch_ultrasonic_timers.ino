int echoPin1 = 27;
int echoPin2 = 26;
int trigPin1 = 25;
int trigPin2 = 23;

portMUX_TYPE sensorMux = portMUX_INITIALIZER_UNLOCKED;
volatile SemaphoreHandle_t semaphore;

volatile unsigned long pulseStart;
volatile unsigned long pulseEnd;

void ARDUINO_ISR_ATTR echo1_ISR() {
  int state = digitalRead(echoPin1);
  portENTER_CRITICAL_ISR(&sensorMux);
  if (state == HIGH) {
    pulseStart = micros();
  } else if (state == LOW) {
    pulseEnd = micros();
    xSemaphoreGiveFromISR(semaphore, NULL);
  }
  portEXIT_CRITICAL_ISR(&sensorMux);
}

void print() {
  // Copy pulse length into local to minimize time spent in critical section
  unsigned long pulseLength;
  portENTER_CRITICAL(&sensorMux);
  pulseLength = pulseEnd - pulseStart;
  portEXIT_CRITICAL(&sensorMux);

  double distance = (double)pulseLength / 1000000.0 * 343.0;
  Serial.print("Pulse length: ");
  Serial.println(pulseLength);
  Serial.print(", Distance: ");
  Serial.println(distance);
}

void setup() {
  
  Serial.begin(9600);
  Serial.println("Serial ready");

  pinMode(echoPin1, INPUT);
  pinMode(echoPin2, INPUT);
  pinMode(trigPin1, OUTPUT);
  pinMode(trigPin2, OUTPUT);

  semaphore = xSemaphoreCreateBinary();
}

void loop() {
  if (xSemaphoreTake(semaphore, 0) == pdTRUE) {
    print();
  }

  digitalWrite(trigPin1, HIGH);
  delayMicroseconds(10);
  digitalWrite(trigPin1, LOW);

  delay(100);
}
