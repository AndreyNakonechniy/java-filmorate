package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;

public interface FilmStorage {

    public List<Film> showAllFilms();

    public Film createFilm(Film film);

    public Film updateFilm(Film film);

    public Film getFilmById(long id);

    public Film addLike(long id, long userId);

    public Film deleteLike(long id, long userId);

    public List<Film> getPopularFilms(String count);

}
