package br.com.scouts.enrollments.domain.intent;

import com.example.demo.infrastructure.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class IntentService {

    public static final String SUBJECT = "Cadastro realizado!";
    public static final String MESSAGE = "Você está na lista de espera, em breve entraremos em contato.";
    @Autowired
    private IntentRepository intentRepository;

    @Autowired
    private EmailService emailService;

    public void create(String name, String email, LocalDate birthAt) {
        Intent intent = new Intent(name, email, birthAt);
        intentRepository.save(intent);
        emailService.send(email, SUBJECT, MESSAGE);
    }

}
