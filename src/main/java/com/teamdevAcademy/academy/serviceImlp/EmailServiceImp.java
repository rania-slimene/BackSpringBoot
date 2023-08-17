package com.teamdevAcademy.academy.serviceImlp;

import com.teamdevAcademy.academy.entities.User;
import com.teamdevAcademy.academy.services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImp implements EmailService{

@Autowired
    private JavaMailSender javaMailSender;
    @Value("${app.base.url}")
    private String appBaseUrl;

    public void sendVerificationEmail(User user ) {
        String subject = "Email Verification";
        String verificationLink = appBaseUrl + "/User/verify?verificationToken=" + user.getVerificationToken();

        String message = "Hello " + user.getNom()  + ",\n\n"
                + "Please click the following link to verify your email:\n"
                + verificationLink + "\n\n"
                + "Thank you.";

        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(user.getEmail());
        email.setSubject(subject);
        email.setText(message);

        javaMailSender.send(email);
    }
    public void sendPasswordResetEmail(User user, String newPassword) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(user.getEmail()); // Utilisation de l'e-mail de l'utilisateur
        message.setSubject("Réinitialisation de mot de passe");
        message.setText("Votre nouveau mot de passe : " + newPassword);
        javaMailSender.send(message);
    }}