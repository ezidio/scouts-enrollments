package com.example.demo.web;

import com.example.demo.domain.enrollment.Enrollment;
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
//    @PreAuthorize("hasRole('CREATE_ENROLLMENT')")
    @Secured("ROLE_CREATE_ENROLLMENT")
    public Enrollment create(@RequestParam("intent") IntentId intentId) {
        return service.createTo(intentRepository.findById(intentId)
                .orElseThrow(() -> new IntentNotFound(intentId)));
    }

    @GetMapping
    public Iterable<Enrollment> findAll() {
        return service.findAll();
    }

}
