package br.com.scouts.enrollments.domain.enrollment;

import br.com.scouts.enrollments.domain.intent.Intent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

@Service
public class EnrollmentService {

    @Autowired
    private EnrollmentEmailService emailService;

    @Autowired
    private EnrollmentRepository repository;

    public Enrollment createTo(Intent intent) {
        Enrollment enrollment = new Enrollment(intent);
        repository.save(enrollment);
        emailService.sendNewEnrollmentMail(enrollment);
        return enrollment;
    }

    public Iterable<Enrollment> findAll() {
        return repository.findAll();
    }
}
