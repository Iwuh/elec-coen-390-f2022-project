package com.teamI.librarymonitoring.datacontainers;

import java.util.Date;

public class Announcement {

    private String announcement_data;
    private String Timestamp;

    private Announcement() {}

    public Announcement(String announcement_data, String Timestamp) {
        this.announcement_data = announcement_data;
        this.Timestamp = Timestamp;
    }

    public String getAnnouncement_data() {
        return announcement_data;
    }

    public void setAnnouncement_data(String announcement_data) {
        this.announcement_data = announcement_data;
    }

    public String getTimestamp() {
        return Timestamp;
    }

    public void setTimestamp(String timestamp) {
        Timestamp = timestamp;
    }
}
