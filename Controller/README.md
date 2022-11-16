# Controller code

* `sketch_controller_code` has the current program for the microcontroller.

* Other folders are tests for the ultrasonic sensors which do not work yet.

# Setup

1. Install [Arduino IDE](https://www.arduino.cc/en/software)
2. Install the [ESP32 Arduino core](https://github.com/espressif/arduino-esp32)
3. Install the Firebase ESP32 library by following the instructions in [this tutorial](https://randomnerdtutorials.com/esp32-firebase-realtime-database/)
4. Open the desired sketch and in the top bar next to the Verify/Upload buttons, select the Sparkfun ESP32 Thing board.

# WiFi

To run the main controller code, you need to set up the wifi connection parameters, which are stored in a file that is not checked in to Git.

1. Create a file in `Controller/sketch_controller_code` called `WifiSecrets.h`.
2. Copy the following lines into the newly created file:
```
constexpr auto WIFI_SSID = "";
constexpr auto WIFI_PASSWD = "";
constexpr auto WIFI_CONU_IDENTITY = "";
```
3. To connect on a home network, fill out `WIFI_SSID` with your network's name and `WIFI_PASSWD` with the network's password.
4. To connect on Concordia's network, fill out `WIFI_CONU_IDENTITY` with your netname and `WIFI_PASSWD` with your netname password.