package ru.yandex.practicum.filmorate.service;

import ru.yandex.practicum.filmorate.model.Genre;

import java.util.List;

public interface GenreService {
    public List<Genre> getAllGenres();

    public Genre getGenreById(int id);
}
