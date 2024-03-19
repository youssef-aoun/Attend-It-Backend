package com.attendit.Attend.It.errorresponses;

public class ResetPasswordErrorResponse {
    private String message;

    public ResetPasswordErrorResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
