package ru.yandex.practicum.filmorate.storage;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Mpa;

import java.util.List;

@RequiredArgsConstructor
@Component
public class MpaDbStorage implements MpaStorage {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<Mpa> getAllMpa() {
        return jdbcTemplate.query("select * from mpa", (rs, rowNum) -> new Mpa(rs.getInt("mpa_id"),
                rs.getString("name")));
    }

    @Override
    public Mpa getMpaById(int id) {
        String sql = String.format("select exists (select mpa_id from mpa where mpa_id = '%s')", id);
        Boolean exist = jdbcTemplate.queryForObject(sql, Boolean.class);
        if (!exist) {
            throw new NotFoundException("Рейтинг MPA не найден");
        }

        return jdbcTemplate.queryForObject("select * from mpa where mpa_id = ?", (rs, rowNum) -> new Mpa(rs.getInt("mpa_id"),
                        rs.getString("name")),
                id);
    }
}
