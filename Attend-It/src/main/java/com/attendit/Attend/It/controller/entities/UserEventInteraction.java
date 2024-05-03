package com.attendit.Attend.It.controller.entities;

import com.attendit.Attend.It.dto.SaveRegisterEventRequest;
import com.attendit.Attend.It.entities.event.Event;
import com.attendit.Attend.It.entities.user.User;
import com.attendit.Attend.It.responses.Response;
import com.attendit.Attend.It.security.authentication.AuthenticationService;
import com.attendit.Attend.It.security.config.JWTUtils;
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
    private final JWTUtils jwtUtils;

    @Autowired
    public UserEventInteraction(UserService userService,
                                EventService eventService,
                                AuthenticationService authenticationService,
                                JWTUtils jwtUtils) {
        this.userService = userService;
        this.eventService = eventService;
        this.authenticationService = authenticationService;
        this.jwtUtils = jwtUtils;
    }

    @PostMapping("/save/{eventId}")
    public ResponseEntity<Response> saveEvent(@PathVariable int eventId, @RequestBody SaveRegisterEventRequest request){
        Event event = eventService.findEventById(eventId);
        int userId = jwtUtils.extractUserId(request.getToken());
        User user = userService.findUserById(userId, "ROLE_USER");
        if(event.getOrganizer() == user)
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(
                    cannotSaveEvent(
                            "You cannot save your own events.",
                            403
                    )
            );
        user.saveEvent(event);
        userService.save(user);
        return ResponseEntity.ok(authenticationService.saveEvent(request));
    }

    @PostMapping("/register/{eventId}")
    public ResponseEntity<Response> registerEvent(@PathVariable int eventId, @RequestBody SaveRegisterEventRequest request){
        String token = request.getToken();
        request.setEventId(eventId);
        if (token == null || token.isEmpty()) {
            // Handle the case where the token is null or empty
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    cannotRegisterEvent("Token is null or empty", HttpStatus.BAD_REQUEST.value())
            );
        }
        int userId = jwtUtils.extractUserId(token);
        User user = userService.findUserById(userId, "ROLE_USER");
        if(user == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    cannotRegisterEvent("User is not found", HttpStatus.BAD_REQUEST.value())
            );
        Event event = eventService.findEventById(eventId);
        if(!user.isVerified()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(
                    cannotRegisterEvent(
                    "Please verify your account. You cannot create or register an event unless you're verified",
                    HttpStatus.FORBIDDEN.value())
            );
        }
        if(event.getRemainingSeats() == 0){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(
                    cannotRegisterEvent(
                            "Unfortunately, the event fully booked.",
                            HttpStatus.FORBIDDEN.value()
                    )
            );
        }
        if(user.getEventsAttended().contains(event))
            return ResponseEntity.status(HttpStatus.CONFLICT).body(
                    cannotRegisterEvent(
                            "You already registered to this event.",
                            HttpStatus.CONFLICT.value()
                    )
            );
        user.attendEvent(event);
        userService.save(user);
        eventService.save(event);
        return ResponseEntity.ok(authenticationService.registerEvent(request));
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

    private Response cannotRegisterEvent(String message, int status){
        Response signupResponse = new Response();
        signupResponse.setMessage(message);
        signupResponse.setStatus(status);
        return signupResponse;
    }

    private Response cannotSaveEvent(String message, int status){
        Response signupResponse = new Response();
        signupResponse.setMessage(message);
        signupResponse.setStatus(status);
        return signupResponse;
    }
}
