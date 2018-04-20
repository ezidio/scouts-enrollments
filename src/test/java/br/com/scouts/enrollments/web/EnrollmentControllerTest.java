package br.com.scouts.enrollments.web;

import br.com.scouts.enrollments.annotations.ControllerTest;
import br.com.scouts.enrollments.domain.IntentEnvironment;
import br.com.scouts.enrollments.domain.Scripts;
import br.com.scouts.enrollments.domain.intent.Intent;
import br.com.scouts.enrollments.domain.intent.exception.IntentNotFound;
import br.com.scouts.enrollments.infrastructure.EmailService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.WebApplicationContext;

import javax.transaction.Transactional;
import java.util.UUID;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
//@ControllerTest
@Transactional
@SpringBootTest
@AutoConfigureTestDatabase
@AutoConfigureTestEntityManager
public class EnrollmentControllerTest {

    @Autowired
    private TestEntityManager testEntityManager;
    @Autowired
    private WebApplicationContext wac;
    @Autowired
    private EnrollmentController controller;

    private MockMvc mockMvc;

    @MockBean
    private EmailService emailService;

    @Before
    public void setUp() throws Exception {
        this.mockMvc = MockMvcBuilders
                .webAppContextSetup(wac)
                .alwaysDo(print())
                .apply(SecurityMockMvcConfigurers.springSecurity())
                .build();
    }

    @Test
    @Sql({Scripts.INTENTS, Scripts.ENROLLMENT})
    public void should_get_all() throws Exception {
        this.mockMvc.perform(
                get("/enrollments")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    public void should_create() throws Exception {

        Intent maria = testEntityManager.persist(IntentEnvironment.createMaria());
        testEntityManager.flush();

        this.mockMvc
                .perform(
                        post("/enrollments")
                                .content("{}")
                                .contentType(MediaType.APPLICATION_JSON_UTF8)
                                .param("intent", maria.getId().toString())
                                .with(csrf())
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(maria.getName())))
                .andExpect(jsonPath("$.email", is(maria.getEmail())));
    }


    @Test
    public void should_not_create_if_not_exists() throws Exception {
        this.mockMvc.perform(
                post("/enrollments")
                        .content("{}")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .param("intent", UUID.randomUUID().toString())
                        .with(csrf()))
                .andExpect(status().isNotFound());
    }

}
