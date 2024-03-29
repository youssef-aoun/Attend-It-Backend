package com.attendit.Attend.It.dto;

import com.attendit.Attend.It.entities.event.Event;

public class EventCreationRequest {
    private Event event;
    private int organizerId;

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public int getOrganizerId() {
        return organizerId;
    }

    public void setOrganizerId(int organizerId) {
        this.organizerId = organizerId;
    }
}
