package br.com.scouts.enrollments.domain;

import br.com.scouts.enrollments.domain.intent.Intent;

import java.time.LocalDate;
import java.time.Month;

public class IntentEnvironment {

    public static Intent createJose() {
        return new Intent("Jose", "jose@teste.com",LocalDate.of(2007, Month.JULY, 10));
    }

    public static Intent createMaria() {
        return new Intent("Maria", "maria@teste.com", LocalDate.of(2008, Month.DECEMBER, 7));
    }

}
