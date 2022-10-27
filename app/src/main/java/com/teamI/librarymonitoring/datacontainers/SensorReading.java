package com.teamI.librarymonitoring.datacontainers;

// this is a simple class to contain sensor readings
// used to populate the recycler views

public class SensorReading {
    protected String strMeasurement;
    protected String strUnit;
    protected String strLocation;

    public SensorReading(String location, String measurement, String unit) {
        this.strMeasurement = measurement;
        this.strLocation = location;
        this.strUnit = unit;
    }

    public String getMeasurement() {
        return strMeasurement;
    }

    public void setMeasurement(String strMeasurement) {
        this.strMeasurement = strMeasurement;
    }

    public String getLocation() {
        return strLocation;
    }

    public void setLocation(String strLocation) {
        this.strLocation = strLocation;
    }

    public String getUnit() {
        return strUnit;
    }

    public void setUnit(String strUnit) {
        this.strUnit = strUnit;
    }
}
