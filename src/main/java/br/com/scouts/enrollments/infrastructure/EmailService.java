package br.com.scouts.enrollments.infrastructure;

public interface EmailService {
    void send(String email, String subject, String message);
}
