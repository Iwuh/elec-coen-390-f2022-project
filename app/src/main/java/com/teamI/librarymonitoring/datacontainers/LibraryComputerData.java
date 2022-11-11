package com.teamI.librarymonitoring.datacontainers;

import java.util.HashMap;
import java.util.Map;

public class LibraryComputerData {

    private String strLibraryName;

    private Map<String, Integer> mapDesktopsInRooms;

    private int nLaptops;

    private int nTablets;

    public LibraryComputerData() {
        this.mapDesktopsInRooms = new HashMap<String, Integer>();
    }


    public String getLibraryName() {
        return strLibraryName;
    }

    public void setLibraryName(String strLibraryName) {
        this.strLibraryName = strLibraryName;
    }

    public Map<String, Integer> getMapDesktopsInRooms() {
        return mapDesktopsInRooms;
    }

    public void setMapDesktopsInRooms(Map<String, Integer> mapDesktopsInRooms) {
        this.mapDesktopsInRooms = mapDesktopsInRooms;
    }

    public int getLaptops() {
        return nLaptops;
    }

    public void setLaptops(int nLaptops) {
        this.nLaptops = nLaptops;
    }

    public int getTablets() {
        return nTablets;
    }

    public void setTablets(int nTablets) {
        this.nTablets = nTablets;
    }

    public void addPairToDesktopMap(String roomName, Integer nDesktops){
        this.mapDesktopsInRooms.put(roomName, nDesktops);
    }
}
