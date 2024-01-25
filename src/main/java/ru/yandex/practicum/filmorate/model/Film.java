package ru.yandex.practicum.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
public class Film implements Comparable<Film> {
    private long id;
    private String name;
    private String description;
    private LocalDate releaseDate;
    private long duration;
    private Set<Long> likes;

    @Override
    public int compareTo(Film o) {
        if (this.getLikes() == null) {
            this.setLikes(new HashSet<>());
        }
        if (o.getLikes() == null) {
            o.setLikes(new HashSet<>());
        }
        return this.getLikes().size() - o.getLikes().size();
    }
}
