package br.com.scouts.enrollments.domain.intent;

import com.example.demo.domain.Scripts;
import com.example.demo.domain.intent.Intent;
import com.example.demo.domain.intent.IntentRepository;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.time.Month;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
//@DataJpaTest
//@Transactional
@SpringBootTest
@AutoConfigureTestDatabase
//@AutoConfigureTestEntityManager
public class IntentRepositoryTest {

//    @Autowired
//    private TestEntityManager testEntityManager;
    
    @Autowired
    private IntentRepository intentRepository;


    @Test
    public void should_find_all() {
//        testEntityManager.persist(IntentEnvironment.createJose());
//        testEntityManager.persist(IntentEnvironment.createMaria());
//        testEntityManager.flush();

        Iterable<Intent> result = intentRepository.findAll();

        Assertions.assertThat(result)
                .isNotNull()
                .hasSize(2);
        Assertions.assertThat(result)
                .extracting(Intent::getName)
                .contains("Jose", "Maria");
    }

    @Test
//    @Sql(value = Scripts.INTENTS)
    public void should_find_ordered() {
        Iterable<Intent> result = intentRepository.findTop10ByOrderByCreatedAtAsc();

        Assertions.assertThat(result)
                .isNotNull()
                .hasSize(10)
                .extracting(Intent::getEmail)
                .containsExactly(
                        "z.bonitinho@teste.com",
                        "n.capitinga@teste.com",
                        "ptolomeu@teste.com",
                        "gaudencio@teste.com",
                        "r.nonato@teste.com",
                        "a.vigario@teste.com",
                        "catifunda@teste.com",
                        "r.lero@teste.com",
                        "j.barbacena@teste.com",
                        "d.cacilda@teste.com"
                        );
    }


    @Test
//    @Sql(value = {Scripts.INTENTS, Scripts.ENROLLMENT})
    public void should_find_ordered_with_no_enrollment() {

        Pageable topTen = PageRequest.of(0, 10);
        List<Intent> result = intentRepository.findWaitingList(topTen);

        Assertions.assertThat(result)
                .isNotNull()
                .hasSize(10)
                .extracting(Intent::getEmail)
                .containsExactly(
                        "z.bonitinho@teste.com",
                        "n.capitinga@teste.com",
                        "ptolomeu@teste.com",
                        "gaudencio@teste.com",
                        "a.vigario@teste.com",
                        "catifunda@teste.com",
                        "r.lero@teste.com",
                        "j.barbacena@teste.com",
                        "d.cacilda@teste.com",
                        "s.muchiba@teste.com"
                );
    }


    @Test
    @Sql(value = {Scripts.INTENTS, Scripts.ENROLLMENT})
    public void should_find_ordered_with_no_enrollment_and_with_accepted_age() {

        LocalDate referenceDate = LocalDate.of(2018, Month.MARCH, 30);
        Period startAge = Period.of(6, 6, 0);
        Period endAge = Period.of(10, 0, 0);

        int minageDays = (int) ChronoUnit.DAYS.between(referenceDate.minus(startAge), referenceDate);
        int maxAgeDays = (int) ChronoUnit.DAYS.between(referenceDate.minus(endAge), referenceDate);

        Pageable topTen = PageRequest.of(0, 10);
        List<Intent> result = intentRepository.findAcceptedAgeWaitingList(minageDays, maxAgeDays, topTen);

        Assertions.assertThat(result)
                .isNotNull()
                .hasSize(10)
                .extracting(Intent::getEmail)
                .containsExactly(
                        "n.capitinga@teste.com",
                        "ptolomeu@teste.com",
                        "gaudencio@teste.com",
                        "a.vigario@teste.com",
                        "r.lero@teste.com",
                        "d.cacilda@teste.com",
                        "s.muchiba@teste.com",
                        "rosinha@teste.com",
                        "p.pedreiro@teste.com",
                        "s.boneco@teste.com"
                );
    }

}