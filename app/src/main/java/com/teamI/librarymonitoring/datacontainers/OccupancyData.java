package com.teamI.librarymonitoring.datacontainers;

public class OccupancyData {

        private String Occupancy;
        private String LibraryName;
        private String LastRecordTime;

        public String getOccupancy(){return Occupancy;}

        public String getLibraryName(){return LibraryName;}

        public String getLastRecordTime(){return LastRecordTime;}

        public void setOccupancy(String Occupancy){this.Occupancy = Occupancy;}
        public void setLibraryName(String LibraryName){this.LibraryName = LibraryName;}
        public void setLastRecordTime(String LastRecordTime){this.LastRecordTime = LastRecordTime;}

        @Override
        public String toString() {
                return "OccupancyData {" +
                        "Occupancy='" + Occupancy + '\'' +
                        ", LibraryName='" + LibraryName + '\'' +
                        ", LastRecordTime='" + LastRecordTime + '\'' +
                        '}';
        }
}

