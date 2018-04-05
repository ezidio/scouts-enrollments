package com.example.demo;

import com.example.demo.domain.IntentEnvironment;
import com.example.demo.domain.Scripts;
import com.example.demo.domain.intent.Intent;
import com.example.demo.infrastructure.EmailService;
import com.example.demo.web.EnrollmentController;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.security.web.context.SecurityContextPersistenceFilter;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@Transactional
@SpringBootTest
@WebAppConfiguration
@ContextConfiguration
@AutoConfigureTestDatabase
@AutoConfigureTestEntityManager
//@WithMockUser(roles = "CREATE_ENROLLMENT")
public class SpringMvcTests {

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
                .apply(SecurityMockMvcConfigurers.springSecurity())
                .alwaysExpect(content().contentType("application/json;charset=UTF-8"))
                .build();
    }

    @Test
    public void should_create() throws Exception {

        Intent maria = testEntityManager.persist(IntentEnvironment.createMaria());
        testEntityManager.flush();

        this.mockMvc
                .perform(
                        post("/enrollments")
                                .content("{}")
                                .param("intent", maria.getId().toString())
                        .with(csrf())
                        .with(user("teste").roles("CREATE_ENROLLMENT"))

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
                        .with(csrf())
                        .param("intent", UUID.randomUUID().toString()))
                .andExpect(status().isNotFound());
    }

    @Test
    @Sql({Scripts.INTENTS, Scripts.ENROLLMENT})
    @WithMockUser
    public void should_get_all() throws Exception {
        this.mockMvc.perform(get("/enrollments"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }
}
