package com.attendit.Attend.It.dto;

import com.attendit.Attend.It.entities.event.Event;
import com.attendit.Attend.It.entities.user.User;

public class SaveRegisterEventRequest {

    private int eventId;
    private String token;

    public SaveRegisterEventRequest() {
    }

    public SaveRegisterEventRequest(int eventId, String token) {
        this.eventId = eventId;
        this.token = token;
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
