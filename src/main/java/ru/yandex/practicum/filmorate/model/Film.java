package ru.yandex.practicum.filmorate.model;

import jdk.dynalink.linker.LinkerServices;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.lang.reflect.Array;
import java.time.LocalDate;
import java.util.*;

@Data
@AllArgsConstructor
public class Film  {
    private long id;
    private String name;
    private String description;
    private LocalDate releaseDate;
    private long duration;
    private Mpa mpa;

//    private List<Genre> genres;

//    public Film(long id, String name, String description, LocalDate releaseDate, long duration, Mpa mpa) {
//        this.id = id;
//        this.name = name;
//        this.description = description;
//        this.releaseDate = releaseDate;
//        this.duration = duration;
//        this.mpa = mpa;
//    }

    //    @Override
//    public int compareTo(Film o) {
//        if (this.getLikes() == null) {
//            this.setLikes(new HashSet<>());
//        }
//        if (o.getLikes() == null) {
//            o.setLikes(new HashSet<>());
//        }
//        return this.getLikes().size() - o.getLikes().size();
//    }
}
