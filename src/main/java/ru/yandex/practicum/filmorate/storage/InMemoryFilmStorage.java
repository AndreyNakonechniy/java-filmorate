//package ru.yandex.practicum.filmorate.storage;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Component;
//import ru.yandex.practicum.filmorate.exception.NotFoundException;
//import ru.yandex.practicum.filmorate.model.Film;
//
//import java.util.*;
//
//
//@Component
//@RequiredArgsConstructor
//public class InMemoryFilmStorage implements FilmStorage {
//
//    private final Map<Long, Film> films = new HashMap<>();
//    private long id = 1L;
//
//    @Override
//    public List<Film> showAllFilms() {
//        return new ArrayList<>(films.values());
//    }
//
//    @Override
//    public Film createFilm(Film film) {
//        return null;
//    }
//
////    @Override
////    public Film createFilm(Film film) {
////        film.setId(id++);
////        film.setLikes(new HashSet<>());
////        films.put(film.getId(), film);
////        return film;
////    }
//
//    @Override
//    public Film updateFilm(Film film) {
//        if (!films.containsKey(film.getId())) {
//            throw new NotFoundException("Фильм не найден");
//        }
//        films.put(film.getId(), film);
//        return film;
//    }
//
//    public Map<Long, Film> getFilmMap() {
//        return films;
//    }
//
//}
