package br.com.scouts.enrollments.domain.enrollment;

import br.com.scouts.enrollments.infrastructure.EmailService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Locale;

import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
@SpringBootTest
//@AutoConfigureTestDatabase
public class EnrollmentEmailServiceTest {

	private static final String EMAIL = "everton.tavares.dev.@gmail.com";
	private static final String NAME = "Everton Tavares";

	@Autowired
	private EnrollmentEmailService emailService;

	@MockBean
	private EmailService mockEmailService;

	@Value("${spring.mvc.locale}")
	private String locale;

	@Test
	public void should_send_new_enrollment_mail() {

		Locale.setDefault(new Locale(this.locale));

		Enrollment enrollment = new Enrollment(NAME, EMAIL);
		emailService.sendNewEnrollmentMail(enrollment);

		verify(mockEmailService).send(EMAIL, "Matricula realizada!", "Olá "+NAME+"! Você foi matriculado com sucesso!");
	}

	@Test
	public void should_send_new_enrollment_english_mail() {
		Locale.setDefault(new Locale("en"));
		Enrollment enrollment = new Enrollment(NAME, EMAIL);
		emailService.sendNewEnrollmentMail(enrollment);

		verify(mockEmailService).send(EMAIL,
				"Enrollment created!",
				"Hi "+NAME+"! Welcome to our course!");
	}

}
