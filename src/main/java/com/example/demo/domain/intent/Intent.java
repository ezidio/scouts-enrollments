package com.example.demo.domain.intent;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import java.time.Instant;
import java.time.LocalDate;

@Entity
public class Intent {

    @EmbeddedId
    private IntentId id = new IntentId();
    private String name;
    private String email;
    private LocalDate birth;
    private Instant createdAt = Instant.now();

    protected Intent() {
        // Construtor vazio para serialização
    }

    public Intent(String name, String email, LocalDate birth) {
        this.name = name;
        this.email = email;
        this.birth = birth;
    }

    public IntentId getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LocalDate getBirth() {
        return birth;
    }

    public String getEmail() {
        return email;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Intent{");
        sb.append(id);
        sb.append(" - ").append(name);
        sb.append(" (").append(email).append(')');
        sb.append(", birth=").append(birth);
        sb.append(", createdAt=").append(createdAt);
        sb.append('}');
        return sb.toString();
    }
}
