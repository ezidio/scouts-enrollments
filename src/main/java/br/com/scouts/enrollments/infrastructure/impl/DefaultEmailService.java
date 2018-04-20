package br.com.scouts.enrollments.infrastructure.impl;

import br.com.scouts.enrollments.infrastructure.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class DefaultEmailService implements EmailService {

    @Autowired
    public JavaMailSender emailSender;

    @Value("${application.mail.reply_to}")
    private String replyTo;

    @Value("${application.mail.from}")
    private String from;

    public void send(String email, String subject, String message) {
        SimpleMailMessage emailMessage = new SimpleMailMessage();
        emailMessage.setTo(email);
        emailMessage.setReplyTo(this.replyTo);
        emailMessage.setFrom(this.from);
        emailMessage.setSubject(subject);
        emailMessage.setText(message);
        emailSender.send(emailMessage);
    }
}
