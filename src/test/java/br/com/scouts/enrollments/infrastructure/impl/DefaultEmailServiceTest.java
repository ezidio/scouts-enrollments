package br.com.scouts.enrollments.infrastructure.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureTestDatabase
//@MailConfiguration
//@TestPropertySource("classpath:application.test.properties")
public class DefaultEmailServiceTest {


    private static final String MESSAGE = "corpo da mensagem";
    private static final String SUBJECT = "assunto";
    private static final String EMAIL_TO = "teste@teste.com";
    @MockBean
    private JavaMailSender javaMailSender;

    @Autowired
    private DefaultEmailService service;

    @Test
    public void should_send_message() {

        ArgumentCaptor<SimpleMailMessage> messageCaptor = ArgumentCaptor.forClass(SimpleMailMessage.class);

        service.send(EMAIL_TO, SUBJECT, MESSAGE);

        Mockito.verify(javaMailSender).send(messageCaptor.capture());

        SimpleMailMessage message = messageCaptor.getValue();
        assertThat(message.getText()).isEqualTo(MESSAGE);
        assertThat(message.getSubject()).isEqualTo(SUBJECT);
        assertThat(message.getTo()).contains(EMAIL_TO);
        assertThat(message.getReplyTo()).isEqualTo("test.reply_to@teste.com");
        assertThat(message.getFrom()).isEqualTo("test.from@teste.com");
    }
}