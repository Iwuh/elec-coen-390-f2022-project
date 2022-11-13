package com.teamI.librarymonitoring.datacontainers;

// used to parse JSON from Open Data API

public class ServiceHours {

    private String service;

    private String hoursText;

    public String getService(){
        return service;
    }

    public String getHoursText(){
        return hoursText;
    }

    public void setService(String service){
        this.service = service;
    }

    public void setHoursText(String hoursText){
        this.hoursText = hoursText;
    }
}
