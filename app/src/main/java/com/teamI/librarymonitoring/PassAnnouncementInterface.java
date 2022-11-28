package com.teamI.librarymonitoring;

import com.teamI.librarymonitoring.datacontainers.Announcement;

import java.util.List;

public interface PassAnnouncementInterface {

    void AnnouncementReceived(List<Announcement> announcement);

}
