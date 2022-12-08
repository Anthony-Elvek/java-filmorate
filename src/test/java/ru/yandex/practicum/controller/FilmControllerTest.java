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
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.yandex.practicum.model.Film;
import java.time.LocalDate;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(FilmController.class)
class FilmControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    private Film film;
    private final LocalDate INVALID_RELEASE_DATE = LocalDate.of(1800, 2, 2);

    @BeforeEach
    void init(){
        film = Film.builder()
                .id(0)
                .name("Some Film")
                .description("Some description")
                .releaseDate(LocalDate.of(2005, 3, 24))
                .duration(121)
                .build();
    }

    @Test
    @DisplayName("POST /films")
    void addFilm() throws Exception {
        String body = objectMapper.writeValueAsString(film);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/films")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("GET /films")
    void getFilms() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/films")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").isNumber());
    }

    @Test
    @DisplayName("PUT /films")
    void updateFilm() throws Exception{
        film.setName("New Film");
        film.setId(1);
        String body = objectMapper.writeValueAsString(film);

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/films")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("New Film"));
    }

    @ParameterizedTest
    @DisplayName("Empty name validation")
    @NullAndEmptySource
    void emptyNameTest(String name) throws Exception {
        film.setName(name);
        String body = objectMapper.writeValueAsString(film);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/films")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Max description validation")
    void descriptionTest() throws Exception {
        StringBuilder stringBuilder = new StringBuilder(film.getDescription());

        while (stringBuilder.length() < 202) {
            stringBuilder.append(" ");
            stringBuilder.append(film.getDescription());
        }

        film.setDescription(stringBuilder.toString());
        String body = objectMapper.writeValueAsString(film);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/films")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isBadRequest());
    }

    @ParameterizedTest
    @ValueSource(ints = {0, -60})
    @DisplayName("Positive duration validation")
    void durationTest(int duration) throws Exception{
        film.setDuration(duration);
        String body = objectMapper.writeValueAsString(film);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/films")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Release date validation")
    void releaseDateTest() throws Exception{
        film.setReleaseDate(INVALID_RELEASE_DATE);

        String body = objectMapper.writeValueAsString(film);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/films")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isBadRequest());
    }
}