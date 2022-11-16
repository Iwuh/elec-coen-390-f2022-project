/******************************************************************************
 * sound_detector_demo.ino
 * Sound detector sample sketch
 * Byron Jacquot @ SparkFun Electronics
 * February 19, 2014
 * https://github.com/sparkfun/Sound_Detector
 * 
 * This sketch demonstrates the use of the Sparkfun Sound Detector board.
 *
 * The Sound Detector is a small board that combines a microphone and some 
 * processing circuitry.  It provides not only an audio output, but also a 
 * binary indication of the presence of sound and an analog representation 
 * of it's amplitude.  
 *
 * This sketch demonstrates two different modes of usage for the Sound
 * Detector.  The gate output (a binary indication that is high when sound
 * is present, and low when conditions are quiet) is used to fire a pin-change 
 * ISR, which lights an LED when the sound is present.  The envelope output 
 * (an analog voltage to rises to indicate the amplitude of the sound) is 
 * sampled in the loop(), and it prints an indication of the level to the 
 * serial terminal. 
 *
 * For more details about the Sound Detector, please check the hookup guide.
 *
 * Connections:
 * The Sound Detector is connected to the Adrduino as follows:
 * (Sound Detector -> Arduino pin)
 * GND → GND
 * VCC → 5V
 * Gate → Pin 2
 * Envelope → A0
 * 
 * Resources:
 * Additional library requirements: none
 * 
 * Development environment specifics:
 * Using Arduino IDe 1.0.5
 * Tested on Redboard, 3.3v/8MHz and 5v/16MHz ProMini hardware.
 * 
 * This code is beerware; if you see me (or any other SparkFun employee) at the
 * local, and you've found our code helpful, please buy us a round!
 * 
 * Distributed as-is; no warranty is given.
 ******************************************************************************/
/*
 // Define hardware connections
#define PIN_GATE_IN 26
#define IRQ_GATE_IN  0
#define PIN_LED_OUT 5
#define PIN_ANALOG_IN 27

// soundISR()
// This function is installed as an interrupt service routine for the pin
// change interrupt.  When digital input 26 changes state, this routine
// is called.
// It queries the state of that pin, and sets the onboard LED to reflect that 
// pin's state.
void soundISR()
{
  int pin_val;

  pin_val = digitalRead(PIN_GATE_IN);
  digitalWrite(PIN_LED_OUT, pin_val);   
}

void setup()
{
  Serial.begin(9600);

  //  Configure LED pin as output
  pinMode(PIN_LED_OUT, OUTPUT);

  // configure input to interrupt
  pinMode(PIN_GATE_IN, INPUT);
  attachInterrupt(IRQ_GATE_IN, soundISR, CHANGE);

  // Display status
  Serial.println("Initialized");
}

void loop()
{
  int value;

  // Check the envelope input
  value = analogRead(PIN_GATE_IN);

  Serial.println(value);


  // Convert envelope value into a message
  Serial.print("Status: ");
  if(value <= 10)
  {
    Serial.println("Quiet.");
  }
  else if( (value > 10) && ( value <= 30) )
  {
    Serial.println("Moderate.");
  }
  else if(value > 30)
  {
    Serial.println("Loud.");
  }
  

  // pause for 1 second
  delay(1000);
}
*/

/*

//https://stackoverflow.com/questions/16556008/representing-music-audio-samples-in-terms-of-db  - Use of PCM 
//dB translation approach 
//https://github.com/tolribeiro/RecNoise/blob/master/Arduino/recnoise.ino
const double dBAnalogQuiet = 10; // envelope - calibrated value from analog input (48 dB equivalent)
const double dBAnalogModerate = 12;
const double dBAnalogLoud = 17;
int count;

#define PIN_QUIET 10
#define PIN_MODERATE 11
#define PIN_LOUD 12

void setup() 
{
  Serial.begin(9600);

  pinMode(PIN_QUIET, OUTPUT);
  pinMode(PIN_MODERATE, OUTPUT);
  pinMode(PIN_LOUD, OUTPUT);
  
  while (!Serial);      
  Serial.println("ESP is connected.");
  restartESP();
  
  pinMode(A0, INPUT);
  count = 0;
}

void loop() 
{
  int value, i;
  float decibelsValueQuiet = 49.5;
  float decibelsValueModerate = 65;
  float decibelsValueLoud = 70;
  float valueDb;
  
  // Check the envelope input
  value = analogRead(PIN_ANALOG_IN);
    
  if (value < 13)
  {
    digitalWrite(PIN_QUIET, HIGH);
    digitalWrite(PIN_MODERATE, LOW);
    digitalWrite(PIN_LOUD, LOW);

    Serial.print("Quiet. Value: ");
    Serial.print(value);
    Serial.print("  ");

    decibelsValueQuiet += calculateDecibels(value, 'q');

    lcd.print(decibelsValueQuiet);
    lcd.print("dB");
    
    Serial.println(decibelsValueQuiet);
    valueDb = decibelsValueQuiet;
    count = count+1;
  }
  else if ((value > 13) && ( value <= 23) )
  {
    digitalWrite(PIN_QUIET, LOW);
    digitalWrite(PIN_MODERATE, HIGH);
    digitalWrite(PIN_LOUD, LOW);

    Serial.print("Moderate. Value: ");
    Serial.print(value);
    Serial.print("  ");
    
    decibelsValueModerate += calculateDecibels(value, 'm');

    Serial.println(decibelsValueModerate);

    valueDb = decibelsValueModerate;
    count = count+1;
  }
  else if(value > 22)
  {
    digitalWrite(PIN_QUIET, LOW);
    digitalWrite(PIN_MODERATE, LOW);
    digitalWrite(PIN_LOUD, HIGH);

    Serial.print("Loud. Value: ");
    Serial.print(value);
    Serial.print("  ");
    
    decibelsValueLoud += calculateDecibels(value, 'l');
    
    Serial.println(decibelsValueLoud);
    
    valueDb = decibelsValueLoud;
    count = count+1;
  }

  delay(400);

float calculateDecibels(int x, char c)
{
  float decibelsCalculated;
  
    if (c == 'q')
      decibelsCalculated = 20 * log10(x/dBAnalogQuiet);
    if (c == 'm')
      decibelsCalculated = 20 * log10(x/dBAnalogModerate);
    if (c == 'l')
      decibelsCalculated = 20 * log10(x/dBAnalogLoud);  
  
  return (decibelsCalculated);
}
*/

//Helpful links
//https://diyi0t.com/sound-sensor-arduino-esp8266-esp32/
//https://www.toptal.com/embedded/esp32-audio-sampling
//https://circuitdigest.com/microcontroller-projects/arduino-sound-level-measurement
//https://forum.arduino.cc/t/converting-envelope-readings-from-sparkfun-sound-detector-to-decibels/362681
//https://docs.arduino.cc/built-in-examples/analog/Smoothing

/*
//ADC conversion approach 
//https://github.com/rmorenojr/ElegooTutorial/blob/master/Lesson%2020%20-%20Sound%20Sensor%20Module/SoundSensor/SoundSensor.ino
void checkAnalog(){
    // checkAnalog reads analog pin, saves min & max values, calculates voltages
    //   Parameters: (none)
    //   calls: drawMeter
    //
    static int maxA = 0;                        // max analog value
    static int minA = 1023;                     // min analog value
    int aValue = 0;                             // raw analog value
    float volts;                                // analog voltage value
    float maxvolts;                             // max voltage value
    float minvolts;                             // min voltage value
                                       
    aValue = analogRead(analogPin);             // analog values from 0-1023, 5V max

    if (aValue < minA) { minA = aValue;}        // save new min value
    if (aValue > maxA) { maxA = aValue;}        // save new max value
    volts = (aValue*5.0)/1024.0;
    minvolts = (minA*5.0)/1024.0;
    maxvolts = (maxA*5.0)/1024.0;

    // begin Debug code - Serial print analog value as decimal
        Serial.print("A = "); Serial.print(aValue,DEC);
        Serial.print(" Max = "); Serial.print(maxA);
        Serial.print(" Min = "); Serial.print(minA);
        Serial.print(" volts = "); Serial.println(volts);
    // end Debug code
}

void checkDigital(){
    // checkDigital reads the digital pin and sets LED state if delay time has expired
    //   Parameters: (none)   
    //
    bool dValue = HIGH;                                 // variable to store digital value
    unsigned long currentMillis = millis();
    
    dValue = digitalRead(digitalPin);           // digital value from D0
    // begin Debug code
        //if (dValue==LOW) Serial.println("Digital Pin reads LOW");
    // end Debug code 
    if(((currentMillis - previousMillis) > delayTime) && (dValue == LOW)){
    //if(dValue == LOW){
        ledState = !ledState;
        digitalWrite(ledPin, ledState);
        previousMillis = currentMillis;
    } 
}
*/

/*

//Serial Plotter - Part 1 - Boolean values plotting
const int OUT_PIN = 27;

void setup() {
  Serial.begin(9600);
}

void loop() {
  Serial.println(digitalRead(OUT_PIN));
}

*/


//Serial Plotter - Part 2 - Converted Values to mimicking sound intensity measurement by sampling
const int OUT_PIN = 15;
const int SAMPLE_TIME = 10;  //number of milliseconds of a single sample
unsigned long millisCurrent; //current time
unsigned long millisLast = 0;//last time
unsigned long millisElapsed = 0; //elapsed time

int sampleBufferValue = 0;  //variable to store the amount of ones in a single sample time
//int AmplitudeArray [9]; //storing specific number computed amplitudes for further noise level investigation
//int arrayCount = 0;

void setup() {
  Serial.begin(9600);
}

void loop() {

  millisCurrent = millis();
  millisElapsed = millisCurrent - millisLast;

  if (digitalRead(OUT_PIN) == HIGH) {
    sampleBufferValue++;
  }

  if (millisElapsed > SAMPLE_TIME) {
    Serial.println(sampleBufferValue);
    /*
    AmplitudeArray[arrayCount++] = sampleBufferValue;

    if(arrayCount == 9){
      for(int i = 0; i < arrayCount; i++){
        if(AmplitudeArray[i] == 0)
          Serial.println("No noise!");
        else if(AmplitudeArray[i] < 200)
          Serial.println("Low Noise Level");
        else if(AmplitudeArray[i] < 500)
          Serial.println("Moderate Noise Level");
        else 
          Serial.println("High Noise Level");
      }   
      arrayCount = 0;
    }
    */
      sampleBufferValue = 0;
      millisLast = millisCurrent;
  }  
}

