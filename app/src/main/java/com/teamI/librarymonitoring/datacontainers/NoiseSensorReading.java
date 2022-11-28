package com.teamI.librarymonitoring.datacontainers;

public class NoiseSensorReading {
    protected String strLocation;
    protected NoiseLevelEnum enNoiseLevel;

    public NoiseSensorReading(String strLocation, Double dSensorReading){
        this.strLocation = strLocation;
        this.enNoiseLevel = NoiseLevelEnum.getNoiseLevel(dSensorReading);
    }

    public String getLocation(){
        return strLocation;
    }

    public NoiseLevelEnum getNoiseLevel(){
        return enNoiseLevel;
    }
}
