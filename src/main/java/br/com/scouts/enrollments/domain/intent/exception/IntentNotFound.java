package br.com.scouts.enrollments.domain.intent.exception;

import br.com.scouts.enrollments.domain.intent.IntentId;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND,reason = "Intent not found")
public class IntentNotFound extends RuntimeException {

    public IntentNotFound(IntentId intentId) {
        super("Intent not found");
    }
}
