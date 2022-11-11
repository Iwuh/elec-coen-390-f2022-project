package com.teamI.librarymonitoring.datacontainers;

import java.util.Date;

public class OccupancyData {

        private int Occupancy;
        private String LibraryName;
        private Date LastRecordTime;

        public int getOccupancy(){return Occupancy;}

        public String getLibraryName(){return LibraryName;}

        public Date getLastRecordTime(){return LastRecordTime;}

        public void setOccupancy(int Occupancy){this.Occupancy = Occupancy;}
        public void setLibraryName(String LibraryName){this.LibraryName = LibraryName;}
        public void setLastRecordTime(Date LastRecordTime){this.LastRecordTime = LastRecordTime;}

        @Override
        public String toString() {
                return "OccupancyData {" +
                        "Occupancy='" + Occupancy + '\'' +
                        ", LibraryName='" + LibraryName + '\'' +
                        ", LastRecordTime='" + LastRecordTime + '\'' +
                        '}';
        }
}

