package com.attendit.Attend.It.controller.dashboard;

import com.attendit.Attend.It.entities.event.Event;
import com.attendit.Attend.It.entities.roles.Role;
import com.attendit.Attend.It.entities.user.User;
import com.attendit.Attend.It.security.config.JWTUtils;
import com.attendit.Attend.It.service.event.EventService;
import com.attendit.Attend.It.service.role.RoleService;
import com.attendit.Attend.It.service.user.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/dashboard")
public class DashboardController {

    private final UserService userService;
    private final EventService eventService;
    private final JWTUtils jwtUtils;
    private final RoleService roleService;

    @Autowired
    public DashboardController(UserService userService,
                               JWTUtils jwtUtils,
                               RoleService roleService,
                               EventService eventService) {
        this.userService = userService;
        this.jwtUtils = jwtUtils;
        this.roleService = roleService;
        this.eventService = eventService;
    }

    @GetMapping("/homepage")
    public String showHome(HttpSession session) {
        String jwtToken = (String) session.getAttribute("jwtToken");
        System.out.println(jwtToken);
        if (jwtToken != null && isValidToken(jwtToken)) {
            // Token is valid, allow access to the home page

            try {
                // Create HttpHeaders and add the JWT token
                HttpHeaders headers = new HttpHeaders();
                headers.set("Authorization", "Bearer " + jwtToken);

                return "/fragments/index";
            } catch (Exception e) {
                // Handle exception
                return "redirect:/error";
            }
        } else {
            // Token is invalid or not present, redirect the user to the login page
            return "redirect:/dashboard/login";
        }
    }


    private boolean isValidToken(String jwtToken) {
        if (userService.findUserById(jwtUtils.extractUserId(jwtToken), "ROLE_ADMIN") != null) {
            return true;
        }
        return false;
    }

    // Add mapping for listing the users
    @GetMapping("/userList")
    public String listUsers(Model model, HttpSession session){
        String jwtToken = (String) session.getAttribute("jwtToken");
        // get employees from db
        if (jwtToken != null && isValidToken(jwtToken)) {
            List<User> userList = userService.findUsersByRole("ROLE_USER", 0);
            // add to the model
            model.addAttribute("users", userList);
            return "fragments/users";
        }
        else {
            // Token is invalid or not present, redirect the user to the login page
            return "redirect:/dashboard/login";
        }
    }



    @GetMapping("/eventList")
    public String listEvents(Model model, HttpSession session){
        String jwtToken = (String) session.getAttribute("jwtToken");
        // get employees from db
        if (jwtToken != null && isValidToken(jwtToken)) {
            List<Event> eventList = eventService.findAll();
            // add to the model
            model.addAttribute("eventList", eventList);
            return "fragments/events";
        }
        else {
            // Token is invalid or not present, redirect the user to the login page
            return "redirect:/dashboard/login";
        }
    }

    @GetMapping("/employeeList")
    public String listEmployees(Model model, HttpSession session){
        String jwtToken = (String) session.getAttribute("jwtToken");
        // get employees from db
        if (jwtToken != null && isValidToken(jwtToken)) {
            List<User> employeeList = userService.findUsersByRole("ROLE_EMPLOYEE", 0);
            List<User> adminList = userService.findUsersByRole("ROLE_ADMIN", 0);
            // add to the model
            model.addAttribute("employees", employeeList);
            model.addAttribute("admins", adminList);
            return "fragments/employees";
        }
        else {
            // Token is invalid or not present, redirect the user to the login page
            return "redirect:/dashboard/login";
        }
    }
    @GetMapping("/showFormForViewEvent")
    public String showFormForViewEvent(@RequestParam("eventId") int theId, Model model, HttpSession session){
        String jwtToken = (String) session.getAttribute("jwtToken");
        if (jwtToken != null && isValidToken(jwtToken)) {
            Event event = eventService.findEventById(theId);
            List<User> attendeesList = event.getAttendees();
            model.addAttribute("event", event);
            model.addAttribute("attendeesList", attendeesList);
            return "fragments/view-event";
        }
        else {
            // Token is invalid or not present, redirect the user to the login page
            return "redirect:/dashboard/login";
        }
    }
    @GetMapping("/showFormForAddUser")
    public String showFormForAddUser(Model model, HttpSession session){
        String jwtToken = (String) session.getAttribute("jwtToken");
        if (jwtToken != null && isValidToken(jwtToken)) {
            model.addAttribute("user", new User());
            return "fragments/add-user";
        }
        else {
            // Token is invalid or not present, redirect the user to the login page
            return "redirect:/dashboard/login";
        }
    }
    @PostMapping("/viewEvent")
    public String viewEvent(@ModelAttribute("event") Event theEvent,
                            HttpSession session,
                            Model model,
                            @RequestParam("eventId") int eventId){
        String jwtToken = (String) session.getAttribute("jwtToken");
        if (jwtToken != null && isValidToken(jwtToken)) {
            Event event = eventService.findEventById(eventId);
            model.addAttribute("event", event);
            return "fragments/edit-event";
        }
        else {
            // Token is invalid or not present, redirect the user to the login page
            return "redirect:/dashboard/login";
        }
    }
    @PostMapping("/save")
    public String saveEmployee(@ModelAttribute("user") User employee, HttpSession session, @RequestParam("role") String role){
        String jwtToken = (String) session.getAttribute("jwtToken");
        Set<Role> roles = new HashSet<>();
        Role userRole = roleService.findRoleByName(role);
        roles.add(userRole);
        if (jwtToken != null && isValidToken(jwtToken)) {
            employee.setRoles(roles);
            userService.save(employee);
            return "redirect:/dashboard/employeeList";
        }
        // use redirect to prevent duplicate submission
        else {
            // Token is invalid or not present, redirect the user to the login page
            return "redirect:/dashboard/login";
        }
    }
    @PostMapping("/updateUser")
    public String updateUser(@ModelAttribute("user") User user,
                             HttpSession session,
                             @RequestParam("verify") boolean verified,
                             @RequestParam("userId") int userId,
                             @RequestParam("suspend") boolean suspended){
        String jwtToken = (String) session.getAttribute("jwtToken");
        User theUser = userService.findUserById(userId, "ROLE_USER");
        if (jwtToken != null && isValidToken(jwtToken)) {
            user.setVerified(verified);
            user.setEnabled(!suspended);
            user.setId(userId);
            user.setUsername(theUser.getUsername());
            user.setPassword(theUser.getPassword());
            user.setRoles(theUser.getRoles());
            userService.save(user);
            return "redirect:/dashboard/userList";
        }
        // use redirect to prevent duplicate submission
        else {
            // Token is invalid or not present, redirect the user to the login page
            return "redirect:/dashboard/login";
        }
    }
    @PostMapping("/updateEmployee")
    public String updateEmployee(
            @ModelAttribute("employee") User user,
            HttpSession session,
            @RequestParam("employeeId") int userId,
            @RequestParam("role") String role){
        String jwtToken = (String) session.getAttribute("jwtToken");
        User theUser = userService.findUserById(userId, "ROLE_EMPLOYEE");
        Set<Role> roles = new HashSet<>();
        Role userRole = roleService.findRoleByName(role);
        roles.add(userRole);
        if (jwtToken != null && isValidToken(jwtToken)) {
            user.setRoles(roles);
            user.setId(userId);
            user.setUsername(theUser.getUsername());
            user.setPassword(theUser.getPassword());
            userService.save(user);
            return "redirect:/dashboard/employeeList";
        }
        // use redirect to prevent duplicate submission
        else {
            // Token is invalid or not present, redirect the user to the login page
            return "redirect:/dashboard/login";
        }
    }
    @PostMapping("/editEvent")
    public String editEvent(
            @ModelAttribute("event") Event theEvent,
            HttpSession session,
            @RequestParam("eventId") int eventId,
            @RequestParam("date") String dateString){
        String jwtToken = (String) session.getAttribute("jwtToken");
        Event event = eventService.findEventById(eventId);
        if (jwtToken != null && isValidToken(jwtToken)) {
            LocalDate date = LocalDate.parse(dateString);
            theEvent.setDate(date);
            theEvent.setId(eventId);
            theEvent.setOrganizer(event.getOrganizer());
            theEvent.setAttendees(event.getAttendees());
            theEvent.setRemainingSeats(theEvent.getNumberOfSeats() - event.getAttendees().size());
            theEvent.setDateOfCreation(event.getDateOfCreation());
            theEvent.setSavedBy(event.getSavedBy());
            eventService.save(theEvent);
            return "redirect:/dashboard/eventList";
        }
        // use redirect to prevent duplicate submission
        else {
            // Token is invalid or not present, redirect the user to the login page
            return "redirect:/dashboard/login";
        }
    }
    @GetMapping("/showFormForUpdateUser")
    public String showFormForUpdateUser(@RequestParam("userId") int theId, Model model, HttpSession session){
        String jwtToken = (String) session.getAttribute("jwtToken");
        if (jwtToken != null && isValidToken(jwtToken)) {
            User user = userService.findUserById(theId, "ROLE_USER");
            model.addAttribute("user", user);
            return "fragments/edit-user";
        }
        else {
            // Token is invalid or not present, redirect the user to the login page
            return "redirect:/dashboard/login";
        }
    }
    @GetMapping("/showFormForUpdateEmployee")
    public String showFormForUpdateEmployee(@RequestParam("userId") int theId, Model model, HttpSession session){
        String jwtToken = (String) session.getAttribute("jwtToken");
        if (jwtToken != null && isValidToken(jwtToken)) {
            User employee = userService.findUserById(theId, "ROLE_EMPLOYEE");
            model.addAttribute("employee", employee);
            return "fragments/edit-employee";
        }
        else {
            // Token is invalid or not present, redirect the user to the login page
            return "redirect:/dashboard/login";
        }
    }

    @GetMapping("/showFormForEditEvent")
    public String showFormForEditEvent(@RequestParam("eventId") int theId, Model model, HttpSession session){
        String jwtToken = (String) session.getAttribute("jwtToken");
        if (jwtToken != null && isValidToken(jwtToken)) {
            Event event = eventService.findEventById(theId);
            model.addAttribute("event", event);
            return "fragments/edit-event";
        }
        else {
            // Token is invalid or not present, redirect the user to the login page
            return "redirect:/dashboard/login";
        }
    }
    @GetMapping("/deleteUser")
    public String deleteUser(@RequestParam("userId") int theId, HttpSession session){
        String jwtToken = (String) session.getAttribute("jwtToken");
        if (jwtToken != null && isValidToken(jwtToken)) {
            userService.deleteUserById(theId);
            return "redirect:/dashboard/userList";
        }
        else {
            // Token is invalid or not present, redirect the user to the login page
            return "redirect:/dashboard/login";
        }
    }
    @GetMapping("/deleteEmployee")
    public String deleteEmployee(@RequestParam("userId") int theId, HttpSession session){
        String jwtToken = (String) session.getAttribute("jwtToken");
        if (jwtToken != null && isValidToken(jwtToken)) {
            userService.deleteUserById(theId);
            return "redirect:/dashboard/employeeList";
        }
        else {
            // Token is invalid or not present, redirect the user to the login page
            return "redirect:/dashboard/login";
        }
    }
    @GetMapping("/deleteEvent")
    public String deleteEvent(@RequestParam("eventId") int theId, HttpSession session){
        String jwtToken = (String) session.getAttribute("jwtToken");
        if (jwtToken != null && isValidToken(jwtToken)) {
            eventService.deleteEventById(theId);
            return "redirect:/dashboard/eventList";
        }
        else {
            // Token is invalid or not present, redirect the user to the login page
            return "redirect:/dashboard/login";
        }
    }

    @GetMapping("removeAttendee")
    public String removeAttendee(@RequestParam("userId") int userID,
                                 @RequestParam("eventId") int eventID,
                                 HttpSession session){
        String jwtToken = (String) session.getAttribute("jwtToken");
        if (jwtToken != null && isValidToken(jwtToken)) {
            User user = userService.findUserById(userID, "ROLE_USER");
            Event event = eventService.findEventById(eventID);
            List<User> attendeesList = event.getAttendees();
            attendeesList.remove(user);
            event.setAttendees(attendeesList);
            event.setRemainingSeats(event.getRemainingSeats()+1);
            eventService.save(event);
            return "redirect:/dashboard/showFormForViewEvent?eventId=" +eventID;
        }
        else {
            // Token is invalid or not present, redirect the user to the login page
            return "redirect:/dashboard/login";
        }
    }
}
