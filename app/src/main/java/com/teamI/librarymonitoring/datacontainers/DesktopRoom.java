package com.teamI.librarymonitoring.datacontainers;


public class DesktopRoom {
    private String strRoomCode;
    private Integer nNumberOfDesktops;

    public DesktopRoom(String strRoomCode, Integer nNumberOfDesktops){
        this.strRoomCode = strRoomCode;
        this.nNumberOfDesktops = nNumberOfDesktops;
    }

    public String getRoomCodeString(){
        return strRoomCode;
    }

    public Integer getNumberOfDesktops(){
        return nNumberOfDesktops;
    }
}
