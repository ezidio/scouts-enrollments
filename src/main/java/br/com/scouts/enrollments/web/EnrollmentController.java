package br.com.scouts.enrollments.web;

import br.com.scouts.enrollments.domain.intent.IntentId;
import br.com.scouts.enrollments.domain.intent.IntentRepository;
import br.com.scouts.enrollments.domain.intent.exception.IntentNotFound;
import com.example.demo.domain.enrollment.Enrollment;
import com.example.demo.domain.enrollment.EnrollmentId;
import com.example.demo.domain.enrollment.EnrollmentService;
import com.example.demo.domain.intent.IntentId;
import com.example.demo.domain.intent.IntentRepository;
import com.example.demo.domain.intent.exception.IntentNotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/enrollments")
public class EnrollmentController {

    @Autowired
    private EnrollmentService service;

    @Autowired
    private IntentRepository intentRepository;

    @PostMapping
    @PreAuthorize("hasRole('CREATE_ENROLLMENT')")
    public Enrollment create(@RequestParam("intent") IntentId intentId) {
        return service.createTo(intentRepository.findById(intentId)
                .orElseThrow(() -> new IntentNotFound(intentId)));
    }

    @GetMapping
    @PreAuthorize("hasRole('READ_ENROLLMENT')")
    public Iterable<Enrollment> findAll() {
        return service.findAll();
    }

}
