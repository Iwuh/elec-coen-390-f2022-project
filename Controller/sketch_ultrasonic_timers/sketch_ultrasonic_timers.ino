int echoPin1 = 27;
int echoPin2 = 26;
int trigPin1 = 25;
int trigPin2 = 23;

hw_timer_t *timer1 = NULL;
portMUX_TYPE timerMux = portMUX_INITIALIZER_UNLOCKED;

volatile int trigTimerCnt;

void ARDUINO_ISR_ATTR timer1_ISR() {
  portENTER_CRITICAL_ISR(&timerMux);
  switch (trigTimerCnt) {
    case 0:
      digitalWrite(trigPin1, HIGH);
      trigTimerCnt++;
      break;
    case 1:
      digitalWrite(trigPin1, LOW);
      trigTimerCnt++;
      break;
    case 5999:
      trigTimerCnt = 0;
      break;
    default:
      trigTimerCnt++;
      break;
  }
  portEXIT_CRITICAL_ISR(&timerMux);
}

void setup() {
  
  Serial.begin(9600);
  Serial.println("Serial ready");

  pinMode(echoPin1, INPUT);
  pinMode(echoPin2, INPUT);
  pinMode(trigPin1, OUTPUT);
  pinMode(trigPin2, OUTPUT);


}

void loop() {
  
}
