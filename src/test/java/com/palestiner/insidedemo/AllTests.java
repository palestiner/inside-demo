package com.palestiner.insidedemo;

import com.palestiner.insidedemo.config.jwt.TokenManager;
import com.palestiner.insidedemo.model.Message;
import com.palestiner.insidedemo.model.User;
import com.palestiner.insidedemo.repo.MessageRepository;
import com.palestiner.insidedemo.repo.UserRepository;
import org.assertj.core.internal.bytebuddy.utility.RandomString;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest(classes = InsideDemoApplication.class)
@TestPropertySource(locations = "classpath:test.properties")
@EnableTransactionManagement
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@AutoConfigureMockMvc
class AllTests {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private TokenManager tokenManager;
    @Autowired
    private MessageRepository messageRepository;
    private static boolean setUpIsDone = false;
    private static String token;
    private static UserDetails userDetails;
    private static Message message;

    @BeforeAll
    private void setUp() {
        if (setUpIsDone) {
            return;
        }
        String username = "user";
        String password = "password";
        User user = new User(username, passwordEncoder.encode(password));
        userRepository.save(user);
        userDetails = new org.springframework.security.core.userdetails.User(username, password, new ArrayList<>());
        token = tokenManager.generateJwtToken(userDetails);
        message = new Message();
        message.setUser(user);
        message.setContent("message 1");
        messageRepository.save(message);
        setUpIsDone = true;
    }

    @Test
    void userDetailsServiceTest_foundUserByUsername_user() {
        String username = userDetailsService.loadUserByUsername("user").getUsername();
        assertEquals("user", username);
    }

    @Test
    void userDetailsServiceTest_userNotFoundException() {
        assertThrows(UsernameNotFoundException.class, () -> {
            String username = RandomString.make(5);
            userDetailsService.loadUserByUsername(username).getUsername();
        });
    }

    @Test
    void tokenManagerTest_generateJwtToken() {
        int actual = token.length();
        assertEquals(172, actual);
    }

    @Test
    void tokenManagerTest_validateJwtToken() {
        assertEquals(true, tokenManager.validateJwtToken(token, userDetails));
    }

    @Test
    void tokenManagerTest_getUsernameFromToken() {
        String usernameFromToken = tokenManager.getUsernameFromToken(token);
        assertEquals("user", usernameFromToken);
    }

    @Test
    void jwtControllerTest_createToken_201Ok() throws Exception {
        String body = "{\"username\": \"user\",\"password\": \"password\"}";
        mockMvc
                .perform(post("/login")
                        .content(body)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.token").exists());
    }

    @Test
    void messageControllerTest_createMessage_201Ok() throws Exception {
        String body = "{\"username\": \"user\",\"message\": \"message 1\"}";
        mockMvc
                .perform(post("/messages")
                        .header("Authorization", "Bearer_" + token)
                        .content(body)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    void messageControllerTest_getLastMessagesByUser_200Ok() throws Exception {
        String body = "{\"username\": \"user\",\"message\": \"history 1\"}";
        mockMvc
                .perform(get("/messages")
                        .header("Authorization", "Bearer_" + token)
                        .content(body)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("*").isNotEmpty());
    }
}
