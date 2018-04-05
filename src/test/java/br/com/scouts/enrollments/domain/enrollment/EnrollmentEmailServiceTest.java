package br.com.scouts.enrollments.domain.enrollment;

import com.example.demo.infrastructure.EmailService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
@SpringBootTest
//@AutoConfigureTestDatabase
public class EnrollmentEmailServiceTest {

	private static final String EMAIL = "everton.tavares.dev.@gmail.com";
	private static final String NAME = "Everton Tavares";

	@Autowired
	private EnrollmentEmailService emailService;

//	@MockBean
//	private EmailService mockEmailService;

	@Test
	public void should_send_new_enrollment_mail() {

		Enrollment enrollment = new Enrollment(NAME, EMAIL);
		emailService.sendNewEnrollmentMail(enrollment);

//		verify(mockEmailService).send(EMAIL, "Matricula realizada!", "Olá "+NAME+"! Você foi matriculado com sucesso!");

	}

}
