package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.validation.FilmValidation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
public class FilmController {
    private final Map<Long, Film> films = new HashMap<>();
    private long id = 1L;

    @GetMapping("/films")
    public List<Film> showAllFilms() {
        return new ArrayList<>(films.values());
    }

    @PostMapping("/films")
    public Film createFilm(@RequestBody Film film) {
        log.info("Creating film {}", film);
        FilmValidation.validate(film);
        film.setId(id++);
        films.put(film.getId(), film);
        return film;
    }

    @PutMapping("/films")
    public Film updateFilm(@RequestBody Film film) {
        log.info("Updating film {}", film);
        FilmValidation.validate(film);
        if (!films.containsKey(film.getId())) {
            throw new ValidationException();
        }
        films.put(film.getId(), film);
        return film;
    }
}
