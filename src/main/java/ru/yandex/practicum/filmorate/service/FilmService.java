package ru.yandex.practicum.filmorate.service;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;

public interface FilmService {
    public List<Film> showAllFilms();

    public Film createFilm(Film film);

    public Film updateFilm(Film film);

    public Film getFilmById(long id);

    public Film addLike(long id, long userId);

    public Film deleteLike(long id, long userId);

    public List<Film> getPopularFilms(int count);
}
