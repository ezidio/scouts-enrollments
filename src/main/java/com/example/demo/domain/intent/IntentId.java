package com.example.demo.domain.intent;

import com.example.demo.infrastructure.Identifier;

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
