package br.com.scouts.enrollments.domain.enrollment;

import br.com.scouts.enrollments.infrastructure.Identifier;

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
