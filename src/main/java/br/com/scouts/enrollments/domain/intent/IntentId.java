package br.com.scouts.enrollments.domain.intent;

import br.com.scouts.enrollments.infrastructure.Identifier;

import javax.persistence.Embeddable;
import java.util.UUID;

@Embeddable
public class IntentId extends Identifier {

    public IntentId(String value) {
        super(value);
    }

    public IntentId(UUID value) {
        super(value);
    }

    public IntentId() {
        super();
    }
}
