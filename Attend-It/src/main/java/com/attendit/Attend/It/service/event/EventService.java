package com.attendit.Attend.It.service.event;

import com.attendit.Attend.It.entities.event.Event;

import java.util.List;

public interface EventService {
    List<Event> findAll();
    Event save(Event event);
    Event findEventById(int id);
    void deleteEventById(int id);
}
