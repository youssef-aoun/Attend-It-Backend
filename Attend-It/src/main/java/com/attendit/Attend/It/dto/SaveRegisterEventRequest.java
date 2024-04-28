package com.attendit.Attend.It.dto;

import com.attendit.Attend.It.entities.event.Event;
import com.attendit.Attend.It.entities.user.User;

public class SaveRegisterEventRequest {

    private Event event;
    private User user;

    public SaveRegisterEventRequest() {
    }

    public SaveRegisterEventRequest(Event event, User user) {
        this.event = event;
        this.user = user;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
