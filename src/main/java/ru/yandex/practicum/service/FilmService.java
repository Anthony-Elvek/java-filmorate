package ru.yandex.practicum.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.exception.NotFoundException;
import ru.yandex.practicum.exception.ValidationException;
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
        final Film film = filmStorage.findById(filmId);

        if (Objects.isNull(film)) {
            throw new NotFoundException(String.format("This film id%d was not found", filmId));
        }

        return film;
    }

    public Film create(Film film) {
        return filmStorage.create(film);
    }

    public Film update(Film film) {
        film = filmStorage.update(film);

        if (Objects.isNull(film)) {
            throw new NotFoundException("This film was not found");
        }

        return film;
    }

    public void addLike(Long filmId, Long userId) {
        final Film film = filmStorage.findById(filmId);
        final User user = userService.findById(userId);

        if (Objects.isNull(film)) {
            throw new NotFoundException(String.format("This film id%d was not found", filmId));
        }
        if (Objects.isNull(user)) {
            throw new NotFoundException(String.format("This user id%d was not found", userId));
        }
        if(film.getLikes().contains(userId)){
            throw new ValidationException("You already like this film before");
        }

        film.getLikes().add(userId);
    }

    public void deleteLike(Long filmId, Long userId) {
        final Film film = filmStorage.findById(filmId);
        final User user = userService.findById(userId);

        if (Objects.isNull(film)) {
            throw new NotFoundException(String.format("This film id%d was not found", filmId));
        }
        if (Objects.isNull(user)) {
            throw new NotFoundException(String.format("This user id%d was not found", userId));
        }

        film.getLikes().remove(userId);
    }

    public List<Film> findPopularFilms(Integer count) {
        return filmStorage.allFilms().stream()
                .sorted(Comparator.comparing(Film::getLikes, (Comparator.comparingInt(Set::size))).reversed())
                .limit(count)
                .collect(Collectors.toList());
    }
}
