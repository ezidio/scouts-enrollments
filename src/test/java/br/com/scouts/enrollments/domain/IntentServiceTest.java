package br.com.scouts.enrollments.domain;

import br.com.scouts.enrollments.DemoApplication;
import br.com.scouts.enrollments.domain.intent.Intent;
import br.com.scouts.enrollments.domain.intent.IntentRepository;
import br.com.scouts.enrollments.domain.intent.IntentService;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {DemoApplication.class})
//@ActiveProfiles("test")
public class IntentServiceTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Autowired
    private IntentService intentService;

    @MockBean
    private IntentRepository intentRepository;

//    @MockBean
//    private EmailService emailService;

    @Test
    public void should_create_intent() {
        intentService.create("Jose", "jose@teste.com", LocalDate.now());
        Mockito.verify(intentRepository).save(Mockito.any(Intent.class));
    }

    @Test
    public void should_not_send_if_save_fail() {
        Mockito.doThrow(new IllegalArgumentException("opa")).when(intentRepository).save(Mockito.any(Intent.class));

        expectedException.expect(IllegalArgumentException.class);
        intentService.create("Jose", "jose@teste.com", LocalDate.now());
    }
}