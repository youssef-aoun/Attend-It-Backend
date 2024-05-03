package com.attendit.Attend.It.controller.entities;

import com.attendit.Attend.It.dto.EventCreationRequest;
import com.attendit.Attend.It.dto.EventUpdateRequest;
import com.attendit.Attend.It.entities.event.Event;
import com.attendit.Attend.It.security.config.JWTUtils;
import com.attendit.Attend.It.service.event.EventService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/events")
public class EventRestController {

    private final EventService eventService;
    private final JWTUtils jwtUtils;

    @Autowired
    public EventRestController(EventService eventService, JWTUtils jwtUtils) {
        this.eventService = eventService;
        this.jwtUtils = jwtUtils;
    }

    @GetMapping("")
    public List<Event> allEvents(){
        return eventService.findAll();
    }

    @GetMapping("/upcoming-events-paging")
    public List<Event> upcomingEventsPageable(@RequestParam(name = "page", defaultValue = "0") int pageNumber){
        return eventService.findUpcomingEvents(pageNumber);
    }

    @GetMapping("/upcoming-events")
    public List<Event> allUpcomingEvents(){
        return eventService.findAllUpcomingEvents();
    }


    @GetMapping("/{eventId}")
    public Event getEventById(@PathVariable int eventId){
        Event event = eventService.findEventById(eventId);
        if(event == null){
            throw new RuntimeException("event not found");
        }
        return event;
    }

    @GetMapping("/search-event/{eventTitle}")
    public List<Event> getEventByTitle(@PathVariable("eventTitle") String eventTitle) {
        eventTitle = eventTitle.replace("-", " "); // Replace hyphens with spaces
        return eventService.findEventByTitle(eventTitle);
    }


    @GetMapping("/previous-events-paging")
    public List<Event> findPreviousEventsPageable(@RequestParam(name = "page", defaultValue = "0") int pageNumber){
        return eventService.findPreviousEvents(pageNumber);
    }

    @GetMapping("/previous-events")
    public List<Event> findAllPreviousEvents(){
        return eventService.findAllPreviousEvents();
    }


    @PostMapping("")
    public Event addEvent(@RequestBody EventCreationRequest eventCreationRequest){
        Event theEvent = eventCreationRequest.getEvent();
        String token = eventCreationRequest.getToken();
        int organizerId = jwtUtils.extractUserId(token);

        theEvent.setTitle(theEvent.getTitle().replace(" ", "-"));
        theEvent.setId(0);
        theEvent.setRemainingSeats(theEvent.getNumberOfSeats());
        return eventService.save(theEvent, organizerId);
    }

    @PutMapping("/{eventId}")
    public Event updateEvent(@PathVariable int eventId, @RequestBody EventUpdateRequest eventUpdateRequest){
        Event eventToUpdate = eventService.findEventById(eventId);
        Event updatedEvent = eventUpdateRequest.getEvent();
        int organizerId = jwtUtils.extractUserId(eventUpdateRequest.getToken());
        updatedEvent.setDate(eventToUpdate.getDate());
        if(updatedEvent.getId() == 0){
            updatedEvent.setId(eventId);
        }
        if(updatedEvent.getNumberOfSeats() == 0){
            updatedEvent.setNumberOfSeats(eventToUpdate.getNumberOfSeats());
        }
        if(eventUpdateRequest.getEvent().getTitle() != null){
            eventUpdateRequest.getEvent().setTitle(eventUpdateRequest.getEvent().getTitle().replace(" ", "-"));
        }
        BeanUtils.copyProperties(updatedEvent, eventToUpdate, getNullPropertyNames(updatedEvent));

        return eventService.save(eventToUpdate);
    }

    @DeleteMapping("/{eventId}")
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

    private String[] getNullPropertyNames(Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<>();
        for (java.beans.PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) {
                emptyNames.add(pd.getName());
            }
        }
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }
}


