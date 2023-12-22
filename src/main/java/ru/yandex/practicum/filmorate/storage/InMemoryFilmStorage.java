package ru.yandex.practicum.filmorate.storage;

import org.apache.logging.log4j.util.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.*;

import static java.util.stream.Collectors.toList;

@Component
public class InMemoryFilmStorage implements FilmStorage {

    private final UserStorage userStorage;

    private final static int DEFAULT_VALUE_FOR_POPULAR_FILMS = 10;

    public InMemoryFilmStorage(UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    private final Map<Long, Film> films = new HashMap<>();
    private long id = 1L;

    @Override
    public List<Film> showAllFilms() {
        return new ArrayList<>(films.values());
    }

    @Override
    public Film createFilm(Film film) {
        film.setId(id++);
        film.setLikes(new HashSet<>());
        films.put(film.getId(), film);
        return film;
    }

    @Override
    public Film updateFilm(Film film) {
        if (!films.containsKey(film.getId())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Фильм не найден");
        }
        films.put(film.getId(), film);
        return film;
    }

    @Override
    public Film getFilmById(long id) {
        if (!films.containsKey(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Фильм не найден");
        }
        return films.get(id);
    }

    @Override
    public Film addLike(long id, long userId) {
        if (!films.containsKey(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Фильм не найден");
        }
        if (userStorage.checkId(userId)) {
            films.get(id).getLikes().add(userId);
        }
        return films.get(id);
    }

    @Override
    public Film deleteLike(long id, long userId) {
        if (!films.containsKey(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Фильм не найден");
        }
        if (userStorage.checkId(userId)) {
            films.get(id).getLikes().remove(userId);
        }
        return films.get(id);
    }

    @Override
    public List<Film> getPopularFilms(String count) {
        List<Film> collect = films.values().stream()
                .sorted(Comparator.reverseOrder())
                .collect(toList());

        if (count == null) {
            return collect.stream()
                    .limit(DEFAULT_VALUE_FOR_POPULAR_FILMS)
                    .collect(toList());
        }

        if (Integer.parseInt(count) <= 0) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Параметр count должен быть больше 0");
        }

        if (collect.size() > Integer.parseInt(count)) {
            return collect.subList(0, Integer.parseInt(count));
        }

        return collect;
    }

}
