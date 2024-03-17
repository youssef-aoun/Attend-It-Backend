package com.attendit.Attend.It.controller.othercontrollers;

import com.attendit.Attend.It.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class ResetPasswordController {
    private final UserService userService;

    @Autowired
    public ResetPasswordController(UserService userService) {
        this.userService = userService;
    }


    // Commented for later development

   /* @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody ResetPasswordRequest request){
        String email = request.getEmail();
        if(userService.findUserByEmail(email) == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResetPasswordErrorResponse("Email not found"));

        String resetToken = UUID.randomUUID().toString();
        userService.sendResetEmail(email, resetToken);
        return ResponseEntity.ok("Please check your email address to reset password");
    }*/
}
