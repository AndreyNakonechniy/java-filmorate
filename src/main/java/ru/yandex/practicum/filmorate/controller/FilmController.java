package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.validation.FilmValidation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
public class FilmController {
    private Map<Integer,Film> films = new HashMap<>();

    @GetMapping("/films")
    public List<Film> showAllFilms(){
        return new ArrayList<>(films.values());
    }

    @PostMapping("/film")
    public Film createFilm(@RequestBody Film film){
        log.info("Получен запрос POST /film.");
        FilmValidation.validate(film);
        films.put(film.getId(),film);
        return film;
    }

    @PutMapping("/film")
    public Film updateFilm(@RequestBody Film film){
        log.info("Получен запрос PUT /film.");
        FilmValidation.validate(film);
        films.put(film.getId(),film);
        return film;
    }
}
