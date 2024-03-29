package com.attendit.Attend.It.dto;

import com.attendit.Attend.It.entities.event.Event;

public class EventUpdateRequest {
    private Event event;

    public EventUpdateRequest() {
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }
}
