package br.com.scouts.enrollments.infrastructure;

import com.fasterxml.jackson.annotation.JsonValue;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@MappedSuperclass
public class Identifier implements Serializable {

    @Column(name = "id")
    @JsonValue
    protected UUID value;

    public Identifier() {
        this(UUID.randomUUID());
    }

    public Identifier(UUID value) {
        this.value = value;
    }

    public Identifier(String value) {
        this(UUID.fromString(value));
    }

    public UUID getValue() {
        return value;
    }

    @Override
    public String toString() {
        return this.value.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Identifier that = (Identifier) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
