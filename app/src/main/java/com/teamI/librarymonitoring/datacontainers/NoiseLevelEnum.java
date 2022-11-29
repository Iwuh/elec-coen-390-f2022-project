package com.teamI.librarymonitoring.datacontainers;

public enum NoiseLevelEnum {
    Error,
    Silent,
    Quiet,
    Moderate,
    Loud;

    private static final Double errorThreshold = 0d;
    private static final Double silentMaximum = 80d;
    private static final Double quietMaximum = 120d;
    private static final Double moderateMaximum = 250d;
    // convert from a noise level to an enum
    public static NoiseLevelEnum getNoiseLevel(Double dSoundSensorReading){
        if(dSoundSensorReading < errorThreshold){
            return NoiseLevelEnum.Error;
        }
        if(dSoundSensorReading < silentMaximum){
            return NoiseLevelEnum.Silent;
        }
        if(dSoundSensorReading < quietMaximum){
            return NoiseLevelEnum.Quiet;
        }
        if(dSoundSensorReading < moderateMaximum){
            return NoiseLevelEnum.Moderate;
        }
        return NoiseLevelEnum.Loud;
    }
}
