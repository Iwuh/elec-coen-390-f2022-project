#include "WifiSecrets.h"

#include <WiFi.h>

class WifiHelper {
public:
  void ConnectToHomeNetwork() {
    Serial.print("Connecting to ");
    Serial.println(WIFI_SSID);
    WiFi.begin(WIFI_SSID, WIFI_PASSWD);

    while (WiFi.status() != WL_CONNECTED) {
      delay(500);
      Serial.print('.');
    }

    Serial.println();
    Serial.print("Connected as ");
    Serial.println(WiFi.localIP());
  }

  void ConnectToConcordiaNetwork() {
    Serial.println("Connecting to ConcordiaUniversity");
    WiFi.begin("ConcordiaUniversity", WPA2_AUTH_PEAP, "", WIFI_CONU_IDENTITY, WIFI_PASSWD);

    while (WiFi.status() != WL_CONNECTED) {
      delay(500);
      Serial.print('.');
    }

    Serial.println();
    Serial.print("Connected as ");
    Serial.println(WiFi.localIP());
  }
};