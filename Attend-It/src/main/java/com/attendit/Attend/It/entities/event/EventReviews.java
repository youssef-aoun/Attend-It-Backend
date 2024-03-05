package com.attendit.Attend.It.entities.event;

import com.attendit.Attend.It.entities.event.keys.EventReviewsKey;
import com.attendit.Attend.It.entities.user.User;
import jakarta.persistence.*;

@Entity
public class EventReviews {

    @EmbeddedId
    EventReviewsKey id;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE,
            CascadeType.PERSIST, CascadeType.REFRESH})
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE,
                CascadeType.PERSIST, CascadeType.REFRESH})
    @MapsId("eventId")
    @JoinColumn(name = "event_id")
    private Event event;

    @Column(name = "comment")
    private String comment;

    @Column(name = "rating")
    private int rating;

    public EventReviews() {
    }

    public EventReviews(String comment, int rating) {
        this.comment = comment;
        this.rating = rating;
    }

    public EventReviewsKey getId() {
        return id;
    }

    public void setId(EventReviewsKey id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    @Override
    public String toString() {
        return "EventReviews{" +
                "comment='" + comment + '\'' +
                ", rating=" + rating +
                '}';
    }
}
