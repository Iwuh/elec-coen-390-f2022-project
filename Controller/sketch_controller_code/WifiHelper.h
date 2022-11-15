#include "WifiSecrets.h"

#include <WiFi.h>
#include <esp_sntp.h>

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

  void StartTimeZoneSynchronization() {
    // Set EST with daylight savings adjustment
    // See https://www.gnu.org/software/libc/manual/html_node/TZ-Variable.html
    setenv("TZ", "EST+5EDT,M3.2.0/2,M11.1.0/2", 1);
    tzset();

    // Start up periodic system clock synchronization
    sntp_setoperatingmode(SNTP_OPMODE_POLL);
    sntp_setservername(0, "pool.ntp.org");
    sntp_init();
  }
};