#include "WifiSecrets.h"

#include <ESP8266WiFi.h>
#include <TZ.h>
#include <coredecls.h>
#include <time.h>

extern "C" {
#include "user_interface.h"
#include "wpa2_enterprise.h"
#include "c_types.h"
}

class WifiHelper {
public:
  void ConnectToHomeNetwork() {
    Serial.print("Connecting to ");
    Serial.println(WIFI_SSID);
    WiFi.mode(WIFI_STA);
    WiFi.disconnect();
    WiFi.begin(WIFI_SSID, WIFI_PASSWD);

    while (WiFi.status() != WL_CONNECTED) {
      delay(1000);
      Serial.print('.');
    }

    Serial.println();
    Serial.print("Connected as ");
    Serial.println(WiFi.localIP());
  }

  void ConnectToConcordiaNetwork() {
    Serial.println("Connecting to ConcordiaUniversity");

    // Connecting to WPA2 Enterprise networks is complicated and we have to use the lower-level ESP8266 library functions
    // See: https://github.com/esp8266/Arduino/issues/1032#issuecomment-258907047
    // See: https://gist.github.com/Matheus-Garbelini/2cd780aed2eddbe17eb4adb5eca42bd6
    WiFi.mode(WIFI_STA);

    wifi_set_opmode(STATION_MODE);

    struct station_config wificfg;
    memset(&wificfg, 0, sizeof(wificfg));
    strcpy((char*)wificfg.ssid, "ConcordiaUniversity");
    strcpy((char*)wificfg.password, WIFI_PASSWD);
    wifi_station_set_config(&wificfg);

    wifi_station_set_wpa2_enterprise_auth(1);

    // Clean up to be sure no old data is still inside
    wifi_station_clear_cert_key();
    wifi_station_clear_enterprise_ca_cert();
    wifi_station_clear_enterprise_identity();
    wifi_station_clear_enterprise_username();
    wifi_station_clear_enterprise_password();
    wifi_station_clear_enterprise_new_password();
    
    wifi_station_set_enterprise_identity((uint8*)WIFI_CONU_IDENTITY, strlen(WIFI_CONU_IDENTITY));
    wifi_station_set_enterprise_username((uint8*)WIFI_CONU_USERNAME, strlen(WIFI_CONU_USERNAME));
    wifi_station_set_enterprise_password((uint8*)WIFI_PASSWD, strlen(WIFI_PASSWD));

    wifi_station_connect();
    while (WiFi.status() != WL_CONNECTED) {
      delay(1000);
      Serial.print('.');
    }

    Serial.println();
    Serial.print("Connected as ");
    Serial.println(WiFi.localIP());
  }

  void StartTimeZoneSynchronization() {
    settimeofday_cb(TimeSyncCallback);
    configTime(TZ_America_Montreal, "pool.ntp.org", "time.nist.gov", "time.google.com");
  }

private:
  static void TimeSyncCallback(bool fromSntp) {
    if (fromSntp) {
      time_t now_sec = time(nullptr);
      char timestampBuffer[64];    
      strftime(timestampBuffer, 64, "%c", localtime(&now_sec));
      Serial.print("Time sync complete. Current time is ");
      Serial.println(timestampBuffer);   
    }
  }
};