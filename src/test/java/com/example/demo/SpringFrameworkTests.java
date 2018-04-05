package com.example.demo;

import com.example.demo.domain.enrollment.Enrollment;
import com.example.demo.domain.enrollment.EnrollmentEmailService;
import com.example.demo.infrastructure.EmailService;
import com.example.demo.web.EnrollmentController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureTestDatabase
@TestPropertySource("classpath:application.test.properties")
public class SpringFrameworkTests {

	private static final String EMAIL = "everton.tavares.dev.@gmail.com";
	private static final String NAME = "Everton Tavares";
	@Autowired
	private EnrollmentEmailService emailService;

	@Autowired
	private EnrollmentController controller;

	@Value("${application.name}")
	private String applicationName;

	@Value("${spring.mail.host}")
	private String mailHost;

	@MockBean
	private EmailService mockEmailService;

	@Test
	public void contextLoads() {

		System.out.println("Nome da aplicação: ");
		System.out.println(this.applicationName);
		System.out.println(this.mailHost);

		Enrollment enrollment = new Enrollment(NAME, EMAIL);
		emailService.sendNewEnrollmentMail(enrollment);

		verify(mockEmailService).send(EMAIL, "Matricula realizada!", "Olá "+NAME+"! Você foi matriculado com sucesso!");

	}

}
