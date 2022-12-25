package ru.yandex.practicum.storage.filmStorage;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.model.Film;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

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
        }
        return film;
    }

    @Override
    public Film findById(Long filmId) {
        return films.get(filmId);
    }

    @Override
    public void addLike(Long filmId, Long userId) {
        films.get(filmId).getLikes().add(userId);
    }

    @Override
    public void deleteLike(Long filmId, Long userId) {
        films.get(filmId).getLikes().remove(userId);
    }

    @Override
    public List<Film> findPopularFilms(int count) {
        return allFilms().stream()
                .sorted(Comparator.comparing(Film::getLikes, (Comparator.comparingInt(Set::size))).reversed())
                .limit(count)
                .collect(Collectors.toList());
    }
}
