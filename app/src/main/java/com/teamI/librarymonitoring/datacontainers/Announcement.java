package com.teamI.librarymonitoring.datacontainers;

import com.google.firebase.database.IgnoreExtraProperties;

import java.util.Date;

public class Announcement {
    private String message;

     private String timestamp;

    public Announcement(){}

    public Announcement(String message,String timeStamp){
        this.message= message;
        this.timestamp = timeStamp;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

        public String getTimestamp() {
        return timestamp;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "Announcement{" +
                "message='" + message + '\'' +
                ", timeStamp='" + timestamp + '\'' +
                '}';
    }
}
