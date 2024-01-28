package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.IncorrectDataException;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.FilmDbStorage;
import ru.yandex.practicum.filmorate.storage.UserStorage;
import ru.yandex.practicum.filmorate.validation.FilmValidation;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class FilmServiceImpl implements FilmService {
    private final FilmDbStorage filmStorage;
    private final UserStorage userStorage;

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
        return filmStorage.addLike(id,userId);
    }

    @Override
    public Film deleteLike(long id, long userId) {
        return filmStorage.deleteLike(id,userId);
    }

    @Override
    public List<Film> getPopularFilms(int count) {
        return filmStorage.getPopularFilms(count);
    }

//    @Override
//    public Film getFilmById(long id) {
//        if (!getFilmStorageMap().containsKey(id)) {
//            throw new NotFoundException("Фильм не найден");
//        }
//        return getFilmStorageMap().get(id);
//    }
}

//    @Override
//    public Film addLike(long id, long userId) {
//        if (!getFilmStorageMap().containsKey(id)) {
//            throw new NotFoundException("Фильм не найден");
//        }
//        if (userStorage.checkId(userId)) {
//            getFilmStorageMap().get(id).getLikes().add(userId);
//        }
//        return getFilmStorageMap().get(id);
//    }

//    @Override
//    public Film deleteLike(long id, long userId) {
//        if (!getFilmStorageMap().containsKey(id)) {
//            throw new NotFoundException("Фильм не найден");
//        }
//        if (userStorage.checkId(userId)) {
//            getFilmStorageMap().get(id).getLikes().remove(userId);
//        }
//        return getFilmStorageMap().get(id);
//    }

//    @Override
//    public List<Film> getPopularFilms(int count) {
//        List<Film> collect = getFilmStorageMap().values().stream()
//                .sorted(Comparator.reverseOrder())
//                .collect(toList());
//
//        if (count <= 0) {
//            throw new IncorrectDataException("Параметр count должен быть больше 0");
//        }
//
//        if (collect.size() > count) {
//            return collect.subList(0, count);
//        }
//
//        return collect;
//    }
//
//    private Map<Long, Film> getFilmStorageMap() {
//        return filmStorage.getFilmMap();
//    }
//}
