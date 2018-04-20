package br.com.scouts.enrollments.web;

import br.com.scouts.enrollments.domain.enrollment.Enrollment;
import br.com.scouts.enrollments.domain.enrollment.EnrollmentService;
import br.com.scouts.enrollments.domain.intent.IntentId;
import br.com.scouts.enrollments.domain.intent.IntentRepository;
import br.com.scouts.enrollments.domain.intent.exception.IntentNotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
    @PreAuthorize("authenticated")
    public Iterable<Enrollment> findAll() {
        return service.findAll();
    }


    @ResponseStatus(value= HttpStatus.NOT_FOUND)
    @ExceptionHandler(IntentNotFound.class)
    public ResponseEntity<String> handle(Exception ex) {
        return ResponseEntity.status(404)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(ex.getMessage());
    }
}
