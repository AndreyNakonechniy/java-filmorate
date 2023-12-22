package ru.yandex.practicum.filmorate.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.validation.FilmValidation;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class FilmServiceImpl implements FilmService {
    private FilmStorage filmStorage;

    @Override
    public List<Film> showAllFilms() {
        return filmStorage.showAllFilms();
    }

    @Override
    public Film createFilm(Film film) {
        FilmValidation.validate(film);
        log.info("Creating film {}", film);
        return filmStorage.createFilm(film);
    }

    @Override
    public Film updateFilm(Film film) {
        FilmValidation.validate(film);
        log.info("Updating film {}", film);
        return filmStorage.updateFilm(film);
    }

    @Override
    public Film getFilmById(long id) {
        log.info("Getting film by id {}", id);
        return filmStorage.getFilmById(id);
    }

    @Override
    public Film addLike(long id, long userId) {
        log.info("Adding like {}", id);
        return filmStorage.addLike(id, userId);
    }

    @Override
    public Film deleteLike(long id, long userId) {
        log.info("Deleting like {}", id);
        return filmStorage.deleteLike(id, userId);
    }

    @Override
    public List<Film> getPopularFilms(String count) {
        log.info("Getting popular films");
        return filmStorage.getPopularFilms(count);
    }
}
