package com.attendit.Attend.It.entities.event;
import com.attendit.Attend.It.entities.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "event")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "title")
    private String title;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "time")
    private LocalTime time;

    @Column(name = "location")
    private String location;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "image")
    private String image;

    @Column(name = "date_of_creation")
    private LocalDateTime dateOfCreation;

    @Column(name = "number_of_seats")
    private int numberOfSeats;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organizer_id")
    @JsonIgnore
    private User organizer;

    @ManyToOne
    @JoinColumn(name = "category_id")
    @JsonIgnore
    private Category category;

    @ManyToMany(fetch = FetchType.LAZY,
                cascade = {CascadeType.DETACH, CascadeType.MERGE,
                CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(
            name = "attended_by",
            joinColumns = @JoinColumn(name = "event_id"),
            inverseJoinColumns = @JoinColumn(name = "attendee_id")
    )
    @JsonIgnore
    private Set<User> attendees;


    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {CascadeType.DETACH, CascadeType.MERGE,
                    CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(
            name = "saved_events",
            joinColumns = @JoinColumn(name = "event_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    @JsonIgnore
    private Set<User> savedBy;

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL)
    Set<EventReviews> reviews;

    private String status;

    public Event() {
    }

    public Event(String title, LocalDate date, LocalTime time, String location, String description) {
        this.title = title;
        this.date = date;
        this.time = time;
        this.location = location;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public LocalDateTime getDateOfCreation() {
        return dateOfCreation;
    }

    public void setDateOfCreation(LocalDateTime dateOfCreation) {
        this.dateOfCreation = dateOfCreation;
    }

    public User getOrganizer() {
        return organizer;
    }

    public void setOrganizer(User organizer) {
        this.organizer = organizer;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Set<User> getAttendees() {
        return attendees;
    }

    public void setAttendees(Set<User> attendees) {
        this.attendees = attendees;
    }

    public Set<User> getSavedBy() {
        return savedBy;
    }

    public void setSavedBy(Set<User> savedBy) {
        this.savedBy = savedBy;
    }

    public String getStatus() {
        setStatus(LocalDate.now());
        return status;
    }

    public int getNumberOfSeats() {
        return numberOfSeats;
    }

    public void setNumberOfSeats(int numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
    }

    public void setStatus(LocalDate currentDate) {
        LocalDate machineDate = LocalDate.now();
        if (date.isBefore(machineDate)) {
            status = "done";
        } else if (date.isEqual(machineDate)) {
            status = "ongoing";
        } else {
            status = "upcoming";
        }
    }

    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", date=" + date +
                ", time=" + time +
                ", location='" + location + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    public void addAttendee(User user){
        if(attendees == null)
            attendees = new HashSet<>();
        attendees.add(user);
    }

    public void saveEvent(User user){
        if(savedBy == null)
            savedBy = new HashSet<>();
        savedBy.add(user);
    }

    public void addReviewToEvent(EventReviews review){
        if(reviews == null)
            reviews = new HashSet<>();
        reviews.add(review);
    }
}
