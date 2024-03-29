package com.attendit.Attend.It.service.event;

import com.attendit.Attend.It.entities.event.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface EventService {
    List<Event> findAll(int pageNumber);
    List<Event> findAll();
    Event save(Event event, int organizerId);
    Event save(Event event);
    Event findEventById(int id);
    void deleteEventById(int id);
    List<Event> findPreviousEvents(int pageNumber);
    List<Event> findAllPreviousEvents();
    Event findEventByTitle(String eventTitle);
    List<Event> findUpcomingEvents(int pageNumber);
    List<Event> findAllUpcomingEvents();

}