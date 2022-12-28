package ru.yandex.practicum.storage.filmStorage;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.exception.NotFoundException;
import ru.yandex.practicum.model.Film;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

@Component
public class InMemoryFilmStorage implements FilmStorage {

    private final Map<Long, Film> films = new HashMap<>();
    private AtomicLong id = new AtomicLong();

    private Long getId() {
        return id.incrementAndGet();
    }

    @Override
    public List<Film> allFilms() {
        return new ArrayList<>(films.values());
    }

    @Override
    public Film create(Film film) {
        film.setId(getId());
        films.put(film.getId(), film);
        return film;
    }

    @Override
    public Film update(Film film) {
        if (films.containsKey(film.getId())) {
            films.put(film.getId(), film);
        } else {
            throw new NotFoundException("This film is not found");
        }
        return film;
    }

    @Override
    public Film findById(Long filmId) {
        Film film;

        if (films.containsKey(filmId)) {
            film = films.get(filmId);
        } else {
            throw new NotFoundException(String.format("This film id%d is not found", filmId));
        }

        return film;
    }
}
