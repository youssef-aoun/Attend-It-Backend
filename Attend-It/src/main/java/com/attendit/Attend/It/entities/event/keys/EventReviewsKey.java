package com.attendit.Attend.It.entities.event.keys;


import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class EventReviewsKey implements Serializable {

    @Column(name = "user_id")
    private int userId;

    @Column(name = "event_id")
    private int eventId;

    public EventReviewsKey(int userId, int eventId) {
        this.userId = userId;
        this.eventId = eventId;
    }

    public EventReviewsKey() {
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }


}
