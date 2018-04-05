package br.com.scouts.enrollments.domain.enrollment;

import com.example.demo.infrastructure.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Service;

@Service
public class EnrollmentEmailService {

    private static final String CREATED_SUBJECT = "enrollment.email.created.subject";
    private static final String CREATED_TEXT = "enrollment.email.created.text";

    @Autowired
    private MessageSourceAccessor messageSource;

    @Autowired
    private EmailService emailService;


    public void sendNewEnrollmentMail(Enrollment enrollment) {
        String subject = messageSource.getMessage(CREATED_SUBJECT);
        String text = messageSource.getMessage(CREATED_TEXT, new String[] {
            enrollment.getName()
        });

        emailService.send(enrollment.getEmail(), subject, text);
    }

}
