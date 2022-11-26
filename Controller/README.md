# Controller code

* `sketch_controller_code` has the current program for the microcontroller.

* Other folders are test programs used during development.

# Setup

1. Install [Arduino IDE](https://www.arduino.cc/en/software)
2. Install the [ESP8266 Arduino core](https://github.com/esp8266/Arduino)
3. Install the Firebase ESP8266/ESP32 library by following the instructions in [this tutorial](https://randomnerdtutorials.com/esp32-firebase-realtime-database/)
4. Open the desired sketch and in the top bar next to the Verify/Upload buttons, select the `LOLIN(WeMos) D1 R1 board.

# WiFi

To run the main controller code, you need to set up the wifi connection parameters, which are stored in a file that is not checked in to Git.

1. Create a file in `Controller/sketch_controller_code` called `WifiSecrets.h`.
2. Copy the following lines into the newly created file:
```
constexpr auto WIFI_SSID = "";
constexpr auto WIFI_PASSWD = "";
constexpr auto WIFI_CONU_IDENTITY = "";
constexpr auto WIFI_CONU_USERNAME = "";
```
3. To connect on a home network, fill out `WIFI_SSID` with your network's name and `WIFI_PASSWD` with the network's password.
4. To connect on Concordia's network, fill out `WIFI_CONU_IDENTITY` with your netname and `WIFI_PASSWD` with your netname password. Leave `WIFI_CONU_USERNAME` empty.

# Wiring

Connect the ultrasonic and sound sensors to the breadboard.
Power the sensors using the controller's 5V and GND pins.
Wire the ultrasonic trigger/echo pins and the sound sensor's envelope pin to the board according to the pin assignments.

# Functionality

The ultrasonic sensors functionality is described in the below finite-state machine. 
On startup, a threshold to detect a person is computed.
When something is detected below that threshold, the state changes to detected.
When the object moves, the state returns to undetected and a sensor event is logged.

![Ultrasonic sensor Mealy FSM](ultrasonic_fsm.png?raw=true "Ultrasonic sensor Mealy FSM")