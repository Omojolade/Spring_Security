package com.decagon.springsecurityjwt.integrations;

import com.decagon.springsecurityjwt.dto.AppUserRequest;
import com.decagon.springsecurityjwt.dto.LoginRequest;
import com.decagon.springsecurityjwt.model.AppUser;
import com.decagon.springsecurityjwt.repository.AppUserRepository;
import com.decagon.springsecurityjwt.setup.GetJwt;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static com.decagon.springsecurityjwt.setup.TestUtils.asJsonString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@SpringBootTest // for integration test
@AutoConfigureMockMvc //@SpringBootTest and  @AutoConfigureMockMvc configures MockMvc
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2) // auto configures H2 in memory database.
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AppUserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AppUserRepository appUserRepository;

    @Autowired
    private GetJwt getJwt;

    AppUserRequest appUserRequest = new AppUserRequest();

    @BeforeAll
    public void setUp() {
        appUserRequest.setEmail("user2@app.com");
        appUserRequest.setPassword(passwordEncoder.encode("password"));
    }

    @Test
    @DisplayName("creates a new app user successfully - integrations 🧚🏻‍")
    public void createAppUser() throws Exception {

        mockMvc.perform(post("/user/register")
                .content(asJsonString(appUserRequest))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }
}
