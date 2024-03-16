package com.attendit.Attend.It.errorresponses;

public class LoginErrorResponse {

    private String message;

    // Constructor
    public LoginErrorResponse(String message) {
        this.message = message;
    }

    // Getter and setter methods
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
