package ru.yandex.practicum.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.yandex.practicum.model.User;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    private User user;
    private final LocalDate INVALID_BIRTHDAY_DATE = LocalDate.of(2030, 1, 1);

    @BeforeEach
    void init() {
        user = User.builder()
                .id(0)
                .email("email@mail.ru")
                .login("Login")
                .name("Name")
                .birthday(LocalDate.of(2000, 2, 22))
                .build();
    }

    @Test
    @DisplayName("POST /users")
    void addUser() throws Exception {
        String body = objectMapper.writeValueAsString(user);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("GET /users")
    void getUsers() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/users")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").isNumber());
    }

    @Test
    @DisplayName("PUT /users")
    void updateUser() throws Exception {
        user.setName("New Name");
        user.setId(1);
        String body = objectMapper.writeValueAsString(user);

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("New Name"));
    }

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("User login validation")
    void loginTest(String login) throws Exception{
        user.setLogin(login);
        String body = objectMapper.writeValueAsString(user);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isBadRequest());
    }

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("Check username validation")
    void usernameTest(String name) throws Exception{
        user.setName(name);

        String body = objectMapper.writeValueAsString(user);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Login"));
    }

    @Test
    @DisplayName("User birthday validation")
    void birthdayTest() throws Exception{
        user.setBirthday(INVALID_BIRTHDAY_DATE);
        String body = objectMapper.writeValueAsString(user);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isBadRequest());
    }

    @ParameterizedTest
    @ValueSource(strings = {" ", "user2mail.ru"})
    @DisplayName("User email validation")
    void emailTest(String email) throws Exception{
        user.setEmail(email);
        String body = objectMapper.writeValueAsString(user);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isBadRequest());
    }
}