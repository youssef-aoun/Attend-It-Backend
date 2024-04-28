package com.attendit.Attend.It.controller.dashboard;

import com.attendit.Attend.It.entities.user.User;
import com.attendit.Attend.It.security.authentication.AuthenticationService;
import com.attendit.Attend.It.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class DashboardController {

    private final UserService userService;

    @Autowired
    public DashboardController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/homepage")
    public String showHome() {
        return "fragments/index";
    }

    @GetMapping("/list")
    public String listEmployee(Model model){
        // get employees from db
        List<User> employeeList = userService.findUsersByRole("ROLE_EMPLOYEE", 0);
        // add to the model
        model.addAttribute("employeeList", employeeList);
        return "fragments/employees";
    }

    @GetMapping("/showFormForAdd")
    public String showFormForAdd(Model model){
        model.addAttribute("user", new User());
        return "fragments/add-user";
    }

    @PostMapping("/save")
    public String saveEmployee(@ModelAttribute("user") User employee){
        userService.save(employee);
        // use redirect to prevent duplicate submission
        return "redirect:/list";
    }

    @GetMapping("/showFormForUpdate")
    public String showFormForUpdate(@RequestParam("userId") int theId, Model model){
        User theEmployee = userService.findUserById(theId, "ROLE_EMPLOYEE");
        model.addAttribute("employee", theEmployee);
        return "fragments/edit-user";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam("userId") int theId){
        userService.deleteUserById(theId);
        return "redirect:/list";
    }

}
