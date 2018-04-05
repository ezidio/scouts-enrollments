package com.example.demo.infrastructure;

public interface EmailService {
    void send(String email, String subject, String message);
}
