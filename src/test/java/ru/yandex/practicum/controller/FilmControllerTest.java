package ru.yandex.practicum.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.yandex.practicum.model.Film;
import ru.yandex.practicum.service.FilmService;

import java.time.LocalDate;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(FilmController.class)
class FilmControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private FilmService filmService;

    private Film film;
    private final LocalDate INVALID_RELEASE_DATE = LocalDate.of(1800, 2, 2);

    @BeforeEach
    void init(){
        film = Film.builder()
                .id(0L)
                .name("Some Film")
                .description("Some description")
                .releaseDate(LocalDate.of(2005, 3, 24))
                .duration(121)
                .build();
    }

    @Test
    @DisplayName("POST /films")
    void addFilm() throws Exception {
        Mockito.when(filmService.create(film)).thenReturn(film);
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
        Mockito.when(filmService.allFilms()).thenReturn(List.of(film));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/films")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").isNumber());
    }

    @Test
    @DisplayName("PUT /films")
    void updateFilm() throws Exception{
        Mockito.when(filmService.update(film)).thenReturn(film);

        film.setName("New Film");
        film.setId(1L);
        String body = objectMapper.writeValueAsString(film);

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/films")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk());
    }

    @ParameterizedTest
    @DisplayName("Empty name validation")
    @NullAndEmptySource
    void emptyNameTest(String name) throws Exception {
        Mockito.when(filmService.create(film)).thenReturn(film);

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
        Mockito.when(filmService.create(film)).thenReturn(film);
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
        Mockito.when(filmService.create(film)).thenReturn(film);
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
        Mockito.when(filmService.create(film)).thenReturn(film);
        film.setReleaseDate(INVALID_RELEASE_DATE);

        String body = objectMapper.writeValueAsString(film);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/films")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Find by id")
    void findByFilmIdTest() throws Exception{
        Mockito.when(filmService.findById(film.getId())).thenReturn(film);
        String body = objectMapper.writeValueAsString(film);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/films/{id}", film.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNumber());

    }
}