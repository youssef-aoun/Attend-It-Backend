package com.attendit.Attend.It.controller.entities;

import com.attendit.Attend.It.dto.SaveRegisterEventRequest;
import com.attendit.Attend.It.entities.event.Event;
import com.attendit.Attend.It.entities.user.User;
import com.attendit.Attend.It.responses.Response;
import com.attendit.Attend.It.security.authentication.AuthenticationService;
import com.attendit.Attend.It.service.event.EventService;
import com.attendit.Attend.It.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/action")
public class UserEventInteraction {

    private final UserService userService;
    private final EventService eventService;
    private final AuthenticationService authenticationService;

    @Autowired
    public UserEventInteraction(UserService userService, EventService eventService, AuthenticationService authenticationService) {
        this.userService = userService;
        this.eventService = eventService;
        this.authenticationService = authenticationService;
    }

    @PostMapping("/save/{userId}/{eventId}")
    public ResponseEntity<Response> saveEvent(@PathVariable int userId, @PathVariable int eventId){
        User user = userService.findUserById(userId, "ROLE_USER");
        Event event = eventService.findEventById(eventId);
        user.saveEvent(event);
        userService.save(user);
        SaveRegisterEventRequest saveEventRequest = new SaveRegisterEventRequest(event, user);
        return ResponseEntity.ok(authenticationService.saveEvent(saveEventRequest));
    }

    @PostMapping("/register/{userId}/{eventId}")
    public ResponseEntity<Response> registerEvent(@PathVariable int userId, @PathVariable int eventId){
        User user = userService.findUserById(userId, "ROLE_USER");
        Event event = eventService.findEventById(eventId);
        if(user.isVerified() == false) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(userNotVerified("Please verify your account."));
        }
        user.attendEvent(event);
        userService.save(user);
        SaveRegisterEventRequest saveRegisterEventRequest = new SaveRegisterEventRequest(event, user);
        return ResponseEntity.ok(authenticationService.registerEvent(saveRegisterEventRequest));
    }

    @GetMapping("/savedEvents/{userId}")
    public List<Event> savedEvents(@PathVariable int userId){
        User user = userService.findUserById(userId, "ROLE_USER");
        return eventService.findEventsBySavedBy(user);
    }

    @GetMapping("/attendedEvents/{userId}")
    public List<Event> attendedEvents(@PathVariable int userId){
        User user = userService.findUserById(userId, "ROLE_USER");
        return eventService.findEventsByAttendedBy(user);
    }

    private Response userNotVerified(String message){
        Response signupResponse = new Response();
        signupResponse.setMessage(message);
        signupResponse.setStatus(403);
        return signupResponse;
    }
}
