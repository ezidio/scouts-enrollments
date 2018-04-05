package com.example.demo.domain.enrollment;

import com.example.demo.infrastructure.Identifier;

import javax.persistence.Embeddable;
import java.util.UUID;

@Embeddable
public class EnrollmentId extends Identifier {

    public EnrollmentId() {
        super();
    }

    public EnrollmentId(UUID value) {
        super(value);
    }
}
