import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.context.WebApplicationContext;
import ru.yandex.practicum.controller.FilmController;
import ru.yandex.practicum.model.Film;
import com.google.gson.Gson;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;

@SpringBootTest(classes = FilmController.class)
public class FilmControllerTests {
    private MockMvc mockMvc;
    private Gson gson;
    private Film film;
    @Autowired
    private WebApplicationContext context;

    @BeforeEach
    void init(){
        film = new Film(null, "Some Film", "Some description", LocalDate.of(2005, 3,24), 121);
    }

    @Test
    @DisplayName("GET /films")
    void getFilms() throws Exception {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/films");
        this.mockMvc.perform(request
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
