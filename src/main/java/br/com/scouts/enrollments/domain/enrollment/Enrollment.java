package br.com.scouts.enrollments.domain.enrollment;

import br.com.scouts.enrollments.domain.intent.Intent;
import br.com.scouts.enrollments.domain.intent.IntentId;
import com.example.demo.domain.intent.Intent;
import com.example.demo.domain.intent.IntentId;

import javax.persistence.*;
import java.time.Instant;

@Entity
public class Enrollment {

    @EmbeddedId
    private EnrollmentId id = new EnrollmentId();

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "intent_id"))
    private IntentId intent;

    private String email;

    private String name;

    private Instant createdAt = Instant.now();

    public Enrollment() {
        // Construtor vazio para serialização
    }

    public Enrollment(String name, String email) {
        this.email = email;
        this.name = name;
    }

    public Enrollment(Intent intent) {
        this.intent = intent.getId();
        this.email = intent.getEmail();
        this.name = intent.getName();
    }

    public EnrollmentId getId() {
        return id;
    }

    public IntentId getIntent() {
        return intent;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }


}
