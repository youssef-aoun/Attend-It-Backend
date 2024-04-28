package com.attendit.Attend.It.dto;

import com.attendit.Attend.It.entities.user.User;

public class EditUserRequest {

    private User user;

    public EditUserRequest() {
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
