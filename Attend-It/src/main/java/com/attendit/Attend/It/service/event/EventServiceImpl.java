package com.attendit.Attend.It.service.event;

import com.attendit.Attend.It.dao.EventRepository;
import com.attendit.Attend.It.dao.UserRepository;
import com.attendit.Attend.It.entities.event.Event;
import com.attendit.Attend.It.entities.user.User;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EventServiceImpl implements EventService{

    private final EventRepository eventRepository;
    private final UserRepository userRepository;

    @Autowired
    public EventServiceImpl(EventRepository eventRepository, UserRepository userRepository) {
        this.eventRepository = eventRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<Event> findAll(int pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber, 5); // Create a Pageable object with page 0 and size 2
        Page<Event> page = eventRepository.findAll(pageable); // Perform pagination query
        return page.getContent(); // Return the content of the page
    }

    @Override
    public Event save(Event event, int organizerId) {
        Optional<User> organizerOptional = userRepository.findById(organizerId);
        User organizer = organizerOptional.orElseThrow(() -> new EntityNotFoundException("User not found with id: " + organizerId));
        if(organizer.isVerified() == false)
            throw new EntityNotFoundException("You are not verified, please verify your account");
        event.setOrganizer(organizer);
        event.setDateOfCreation(LocalDateTime.now());
        return eventRepository.save(event);
    }


    @Override
    public Event save(Event event) {
        return eventRepository.save(event);
    }

    @Override
    public Event findEventById(int id) {
        Optional<Event> event = eventRepository.findById(id);
        return event.orElse(null);
    }

    @Override
    public void deleteEventById(int id) {
        eventRepository.deleteById(id);
    }

    @Override
    public List<Event> findAll() {
        List<Event> events = eventRepository.findAll();

        // Use Comparator to compare events by date difference from today
        Comparator<Event> byDateDifference = Comparator.comparingLong(event -> Math.abs(getDateDifference(event.getDate())));

        // Sort events by closest to today's date
        List<Event> sortedEvents = events.stream()
                .sorted(byDateDifference)
                .collect(Collectors.toList());

        return sortedEvents;
    }
    private long getDateDifference(LocalDate eventDate) {
        return Math.abs(eventDate.toEpochDay() - LocalDate.now().toEpochDay());
    }

    @Override
    public List<Event> findPreviousEvents(int pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber, 5);
        Page<Event> page = eventRepository.findAllByDateBefore(LocalDate.now(), pageable);
        return page.getContent();
    }

    @Override
    public List<Event> findEventByTitle(String eventTitle) {
        return eventRepository.findEventByTitle(eventTitle);
    }

    @Override
    public List<Event> findAllPreviousEvents() {
        return eventRepository.findEventByDateBefore(LocalDate.now());
    }

    @Override
    public List<Event> findUpcomingEvents(int pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber, 5);
        Page<Event> page = eventRepository.findAllByDateAfter(LocalDate.now(), pageable);
        return page.getContent();
    }

    @Override
    public List<Event> findAllUpcomingEvents() {
        return eventRepository.findAllByDateAfter(LocalDate.now());
    }

    @Override
    public List<Event> findEventsBySavedBy(User user) {
        return eventRepository.findEventBySavedBy(user);
    }

    @Override
    public List<Event> findEventsByAttendedBy(User user) {
        return eventRepository.findEventByAttendees(user);
    }
}
