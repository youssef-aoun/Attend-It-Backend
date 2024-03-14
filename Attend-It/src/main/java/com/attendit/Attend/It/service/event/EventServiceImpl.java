package com.attendit.Attend.It.service.event;

import com.attendit.Attend.It.dao.EventRepository;
import com.attendit.Attend.It.entities.event.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class EventServiceImpl implements EventService{

    private final EventRepository eventRepository;

    @Autowired
    public EventServiceImpl(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @Override
    public List<Event> findAll(int pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber, 5); // Create a Pageable object with page 0 and size 2
        Page<Event> page = eventRepository.findAll(pageable); // Perform pagination query
        return page.getContent(); // Return the content of the page
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
        return eventRepository.findAll();
    }

    @Override
    public List<Event> findPreviousEvents(int pageNumber) {
        return eventRepository.findEventByDateBefore(LocalDate.now());
    }


}
