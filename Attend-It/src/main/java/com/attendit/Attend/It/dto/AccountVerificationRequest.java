package com.attendit.Attend.It.dto;

import com.attendit.Attend.It.entities.user.User;

public class AccountVerificationRequest {

    private User user;
    private int document1, document2;

    public AccountVerificationRequest() {
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getDocument1() {
        return document1;
    }

    public void setDocument1(int document1) {
        this.document1 = document1;
    }

    public int getDocument2() {
        return document2;
    }

    public void setDocument2(int document2) {
        this.document2 = document2;
    }
}
