package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.FilmDbStorage;
import ru.yandex.practicum.filmorate.validation.FilmValidation;

import java.util.List;


@Service
@RequiredArgsConstructor
public class FilmServiceImpl implements FilmService {
    private final FilmDbStorage filmStorage;

    @Override
    public List<Film> showAllFilms() {
        return filmStorage.showAllFilms();
    }

    @Override
    public Film createFilm(Film film) {
        FilmValidation.validate(film);
        return filmStorage.createFilm(film);
    }

    @Override
    public Film updateFilm(Film film) {
        FilmValidation.validate(film);
        return filmStorage.updateFilm(film);
    }

    @Override
    public Film getFilmById(long id) {
        return filmStorage.getFilmById(id);
    }

    @Override
    public Film addLike(long id, long userId) {
        return filmStorage.addLike(id, userId);
    }

    @Override
    public Film deleteLike(long id, long userId) {
        return filmStorage.deleteLike(id, userId);
    }

    @Override
    public List<Film> getPopularFilms(int count) {
        return filmStorage.getPopularFilms(count);
    }

}
