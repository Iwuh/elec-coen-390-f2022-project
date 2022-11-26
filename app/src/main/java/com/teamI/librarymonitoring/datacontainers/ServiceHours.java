package com.teamI.librarymonitoring.datacontainers;

// used to parse JSON from Open Data API

public class ServiceHours {

    private String service;

    private String hoursText;

    public String getService(){
        return service;
    }

    public String getHoursText(){

        // when the service is open all-day, the API shows "X PM to X PM"
        // in this case, should parse string and simply output "All Day"
        int middleNum = hoursText.indexOf(" to ");
        String strStartTime = hoursText.substring(0, middleNum);
        String strEndTime = hoursText.substring(middleNum + " to ".length());

        if(strStartTime.equals(strEndTime)){
            return "All Day";
        }

        return hoursText;
    }

    public void setService(String service){
        this.service = service;
    }

    public void setHoursText(String hoursText){
        this.hoursText = hoursText;
    }
}
