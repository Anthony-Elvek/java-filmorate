package ru.yandex.practicum.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.model.Film;
import ru.yandex.practicum.model.User;
import ru.yandex.practicum.storage.filmStorage.FilmStorage;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FilmService {
    private final FilmStorage filmStorage;
    private final UserService userService;

    public List<Film> allFilms() {
        return filmStorage.allFilms();
    }

    public Film findById(Long filmId) {
        return filmStorage.findById(filmId);
    }

    public Film create(Film film) {
        return filmStorage.create(film);
    }

    public Film update(Film film) {
        return filmStorage.update(film);
    }

    public void addLike(Long filmId, Long userId) {
        final Film film = filmStorage.findById(filmId);
        final User user = userService.findById(userId);

        film.getLikes().add(user.getId());
    }

    public void deleteLike(Long filmId, Long userId) {
        final Film film = filmStorage.findById(filmId);
        final User user = userService.findById(userId);

        film.getLikes().remove(user.getId());
    }

    public List<Film> findPopularFilms(Integer count) {
        return filmStorage.allFilms().stream()
                .sorted(Comparator.comparing(Film::getLikes, (Comparator.comparingInt(Set::size))).reversed())
                .limit(count)
                .collect(Collectors.toList());
    }
}
