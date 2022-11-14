int echoPin1 = 27;
int echoPin2 = 26;
int trigPin1 = 25;
int trigPin2 = 23;

void setup() {
  // put your setup code here, to run once:
  Serial.begin(9600);
  Serial.println("Serial ready");

  pinMode(echoPin1, INPUT);
  pinMode(echoPin2, INPUT);
  pinMode(trigPin1, OUTPUT);
  pinMode(trigPin2, OUTPUT);
}

void loop() {
  // put your main code here, to run repeatedly:
  digitalWrite(trigPin1, HIGH);
  delayMicroseconds(10);
  digitalWrite(trigPin1, LOW);

  // Duration in microseconds
  unsigned long dur = pulseIn(echoPin1, HIGH);
  Serial.print("Pulse length: ");
  Serial.println(dur);
  double len = (double)dur / 1000000.0 * 343.0;
  Serial.print("Distance: ");
  Serial.println(len);

  delay(1000);
}
