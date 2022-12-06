package ru.yandex.practicum.controller;

import ru.yandex.practicum.exception.ValidationException;
import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.model.Film;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@Slf4j
@RestController
@RequestMapping("/films")
public class FilmController {

    private final Map<Integer, Film> films = new HashMap<>();
    private int id;

    @GetMapping
    public Collection<Film> allFilms(){
        return films.values();
    }

    @PostMapping
    public Film create(@Valid @RequestBody Film film){
        film.setId(++id);
        films.put(film.getId(), film);
        return film;
    }

    @PutMapping
    public Film update(@Valid @RequestBody Film film) {
        if (films.containsKey(film.getId())) {
            films.put(film.getId(), film);
        } else {
            throw new ValidationException("Unknown film");
        }
        return film;
    }
}
