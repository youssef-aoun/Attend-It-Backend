package com.attendit.Attend.It.controller.dashboard;

import com.attendit.Attend.It.dto.LoginRequest;
import com.attendit.Attend.It.responses.Response;
import com.attendit.Attend.It.security.authentication.AuthenticationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/dashboard")
public class DashboardLoginController {

    private final AuthenticationService authenticationService;

    @Autowired
    public DashboardLoginController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @GetMapping("/login")
    public String loginUser(
    ){
        return "/fragments/login";
    }

    @RequestMapping("/processForm")
    public String processForm(HttpServletRequest request, RedirectAttributes redirectAttributes){
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        LoginRequest loginRequest = new LoginRequest(username, password);
        ResponseEntity<Response> response =  ResponseEntity.ok(authenticationService.login(loginRequest));
        // Extract the token from the response body
        if (response.getStatusCode() == HttpStatus.OK) {
            Response responseBody = response.getBody();
            if (responseBody != null) {
                String jwtToken = responseBody.getToken();
                redirectAttributes.addAttribute("token", jwtToken);
            }
        }

        return "redirect:/dashboard/performLogin";
    }

    @GetMapping("/performLogin")
    public String performLogin(
            @RequestParam("token") String jwtToken,
            HttpSession session){
        session.setAttribute("jwtToken", jwtToken);
        return "redirect:/dashboard/homepage";
    }

}
