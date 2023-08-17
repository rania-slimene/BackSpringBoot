package com.teamdevAcademy.academy.services;


import com.teamdevAcademy.academy.entities.User;

public interface EmailService {
    public void sendVerificationEmail(User user);
}
