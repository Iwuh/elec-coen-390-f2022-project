#include "WifiSecrets.h"

#include <WiFi.h>
#include <esp_sntp.h>

class WifiHelper {
public:
  void ConnectToHomeNetwork() {
    Serial.print("Connecting to ");
    Serial.println(WIFI_SSID);
    WiFi.mode(WIFI_STA);
    WiFi.disconnect();
    WiFi.begin(WIFI_SSID, WIFI_PASSWD);

    size_t tries = 0;
    while (WiFi.status() != WL_CONNECTED && tries < MAX_WAIT_BEFORE_RESTART) {
      delay(500);
      Serial.print('.');
      tries++;
    }

    if (WiFi.status() != WL_CONNECTED && tries == MAX_WAIT_BEFORE_RESTART) {
      Serial.println("WiFi connection hanging, restarting controller...");
      ESP.restart();
    }

    Serial.println();
    Serial.print("Connected as ");
    Serial.println(WiFi.localIP());
  }

  void ConnectToConcordiaNetwork() {
    Serial.println("Connecting to ConcordiaUniversity");
    WiFi.mode(WIFI_STA);
    WiFi.disconnect();
    WiFi.begin("ConcordiaUniversity", WPA2_AUTH_PEAP, "", WIFI_CONU_IDENTITY, WIFI_PASSWD);

    size_t tries = 0;
    while (WiFi.status() != WL_CONNECTED && tries < MAX_WAIT_BEFORE_RESTART) {
      delay(500);
      Serial.print('.');
      tries++;
    }

    if (WiFi.status() != WL_CONNECTED && tries == MAX_WAIT_BEFORE_RESTART) {
      Serial.println("WiFi connection hanging, restarting controller...");
      ESP.restart();
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
    sntp_set_time_sync_notification_cb(&TimeSyncCallback);
    sntp_init();
  }

private:
  constexpr static size_t MAX_WAIT_BEFORE_RESTART = 20;

  static void TimeSyncCallback(struct timeval *tv) {
    time_t now_sec = tv->tv_sec;
    char timestampBuffer[64];    
    strftime(timestampBuffer, 64, "%c", localtime(&now_sec));
    Serial.print("Time sync complete. Current time is ");
    Serial.println(timestampBuffer);  
  }
};