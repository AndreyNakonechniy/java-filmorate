package ru.yandex.practicum.filmorate.storage;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Genre;

import java.util.List;

@Component
@RequiredArgsConstructor
public class GenreDbStorage implements GenreStorage {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<Genre> getAllGenres() {
        return jdbcTemplate.query("select * from genres", (rs, rowNum) -> new Genre(rs.getInt("genre_id"),
                rs.getString("name")));
    }

    @Override
    public Genre getGenreById(int id) {
        String sql = String.format("select exists (select genre_id from genres where genre_id = '%s')", id);
        Boolean exist = jdbcTemplate.queryForObject(sql, Boolean.class);
        if (!exist) {
            throw new NotFoundException("Жанр не найден");
        }

        return jdbcTemplate.queryForObject("select * from genres where genre_id = ?", (rs, rowNum) -> new Genre(rs.getInt("genre_id"),
                        rs.getString("name")),
                id);
    }
}
