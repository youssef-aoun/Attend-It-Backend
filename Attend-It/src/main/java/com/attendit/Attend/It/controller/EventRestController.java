package com.attendit.Attend.It.controller;

import com.attendit.Attend.It.entities.event.Event;
import com.attendit.Attend.It.service.event.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class EventRestController {

    private final EventService eventService;

    @Autowired
    public EventRestController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping("/events")
    public List<Event> events(){
        return eventService.findAll();
    }

    @GetMapping("/events/{eventId}")
    public Event getEvent(@PathVariable int eventId){
        Event event = eventService.findEventById(eventId);
        if(event == null){
            throw new RuntimeException("event not found");
        }
        return event;
    }

    @PostMapping("/events")
    public Event addEvent(@RequestBody Event theEvent){
        theEvent.setId(0);
        return eventService.save(theEvent);
    }

    @PutMapping("/events")
    public Event updateEvent(@RequestBody Event event){
        return eventService.save(event);
    }

    @DeleteMapping("/events/{eventId}")
    public String deleteEvent(@PathVariable int eventId){
        Event event = eventService.findEventById(eventId);
        if(event == null){
            return "Event with ID " + eventId + " not found!";
        }
        else {
            eventService.deleteEventById(eventId);
            return "Deleted!";
        }
    }
}
