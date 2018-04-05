package com.example.demo.domain.intent.exception;

import com.example.demo.domain.intent.IntentId;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class IntentNotFound extends RuntimeException {

    public IntentNotFound(IntentId intentId) {
        super("Intent not found");
    }
}
