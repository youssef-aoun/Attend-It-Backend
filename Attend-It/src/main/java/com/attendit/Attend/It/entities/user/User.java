package com.attendit.Attend.It.entities.user;

import com.attendit.Attend.It.entities.event.Event;
import com.attendit.Attend.It.entities.event.EventReviews;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "username", unique = true)
    private String username;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "about_me", columnDefinition = "TEXT")
    private String aboutMe;

    @Column(name = "verified")
    private boolean verified;

    @Column(name = "image")
    private String image;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "organizer")
    private Set<Event> eventsOrganized;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {CascadeType.DETACH, CascadeType.MERGE,
                    CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(
            name = "attended_by",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "event_id")
    )
    private Set<Event> eventsAttended;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {CascadeType.DETACH, CascadeType.MERGE,
                    CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(
            name = "saved_events",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "event_id")
    )
    private Set<Event> eventsSaved;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    Set<EventReviews> reviews;

    public User() {
    }

    public User(String firstName, String lastName, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAboutMe() {
        return aboutMe;
    }

    public void setAboutMe(String aboutMe) {
        this.aboutMe = aboutMe;
    }

    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }


    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }


    public Set<Event> getEventsOrganized() {
        return eventsOrganized;
    }

    public void setEventsOrganized(Set<Event> eventsOrganized) {
        this.eventsOrganized = eventsOrganized;
    }

    public Set<Event> getEventsAttended() {
        return eventsAttended;
    }

    public void setEventsAttended(Set<Event> eventsAttended) {
        this.eventsAttended = eventsAttended;
    }

    public Set<Event> getEventsSaved() {
        return eventsSaved;
    }

    public void setEventsSaved(Set<Event> eventsSaved) {
        this.eventsSaved = eventsSaved;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    public void organizeEvent(Event event){
        if(eventsOrganized == null)
            eventsOrganized = new HashSet<>();
        eventsOrganized.add(event);
    }

    public void attendEvent(Event event){
        if(eventsAttended == null)
            eventsAttended = new HashSet<>();
        eventsAttended.add(event);
    }

    public void saveEvent(Event event){
        if(eventsSaved == null)
            eventsSaved = new HashSet<>();
        eventsSaved.add(event);
    }

    public void addReviewByUser(EventReviews review){
        if(reviews == null)
            reviews = new HashSet<>();
        reviews.add(review);
    }
}
