package br.com.scouts.enrollments.annotations;

import org.springframework.boot.test.autoconfigure.properties.PropertyMapping;

import java.lang.annotation.*;

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface MailConfiguration {

    @PropertyMapping("application.mail.from")
    String from() default "ann.from@teste.com";

    @PropertyMapping("application.mail.reply_to")
    String replyTo() default "ann.reply_to@teste.com";

}
