package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.service.GenreService;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
public class GenreController {
    private final GenreService genreService;

    @GetMapping("/genre")
    public List<Genre> getAllGenres(){
        return genreService.getAllGenres();
    }

    @GetMapping("/genre/{id}")
    public Genre getGenreById(@PathVariable int id){
        log.info("Getting Genre by id {}", id);
        return genreService.getGenreById(id);
    }
}
