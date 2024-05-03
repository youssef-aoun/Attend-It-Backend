package com.attendit.Attend.It.dto;

import com.attendit.Attend.It.entities.event.Event;

public class EventUpdateRequest {
    private Event event;
    private String token;

    public EventUpdateRequest() {
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
