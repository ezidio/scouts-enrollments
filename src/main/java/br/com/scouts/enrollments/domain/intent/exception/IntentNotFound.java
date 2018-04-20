package br.com.scouts.enrollments.domain.intent.exception;

import br.com.scouts.enrollments.domain.intent.IntentId;

public class IntentNotFound extends RuntimeException {

    public IntentNotFound(IntentId intentId) {
        super("Intent not found");
    }
}
