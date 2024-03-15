package com.attendit.Attend.It.errorresponses;

public class SignupErrorResponse {
    private String message;

    // Constructor
    public SignupErrorResponse(String message) {
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
