package ru.yandex.practicum.storage.filmStorage;

import ru.yandex.practicum.model.Film;

import java.util.List;

public interface FilmStorage {
    List<Film> allFilms();

    Film create(Film film);

    Film update(Film film);

    Film findById(Long filmId);
}
