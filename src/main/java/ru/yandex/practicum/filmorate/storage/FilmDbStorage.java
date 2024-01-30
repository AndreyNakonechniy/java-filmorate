package ru.yandex.practicum.filmorate.storage;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Mpa;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Component
public class FilmDbStorage implements FilmStorage {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<Film> showAllFilms() {
        return jdbcTemplate.query("select f.*, m.name as mpa_name from film f join mpa m on f.mpa_id = m.mpa_id",
                (rs, rowNum) -> {
                    int filmId = rs.getInt("film_id");
                    return new Film(filmId,
                            rs.getString("name"),
                            rs.getString("description"),
                            rs.getDate("releaseDate").toLocalDate(),
                            rs.getInt("duration"),
                            new Mpa(rs.getInt("mpa_id"), rs.getString("mpa_name")),
                            getFilmGenre(filmId));
                });
    }

    @Override
    public Film createFilm(Film film) {


        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate.getDataSource())
                .withTableName("film")
                .usingGeneratedKeyColumns("film_id");

        Map<String, Object> params = Map.of("name", film.getName(), "description", film.getDescription(), "releaseDate", film.getReleaseDate(),
                "duration", film.getDuration(), "mpa_id", film.getMpa().getId());
        Number id = simpleJdbcInsert.executeAndReturnKey(params);
        film.setId(id.intValue());
        createOrReplaceFilmGenre(film);

        return film;
    }

    @Override
    public Film updateFilm(Film film) {
        String sql = String.format("select exists(select film_id from film where film_id = '%s')", film.getId());
        Boolean exist = jdbcTemplate.queryForObject(sql, Boolean.class);
        if (!exist) {
            throw new NotFoundException("Фильм не найден");
        }

        jdbcTemplate.update("update film set name = ?, description = ?, releaseDate = ?, duration = ?, mpa_id = ? where film_id = ?",
                film.getName(), film.getDescription(), film.getReleaseDate(), film.getDuration(), film.getMpa().getId(), film.getId());
        createOrReplaceFilmGenre(film);
        return film;
    }

    @Override
    public Film getFilmById(long id) {
        String sql = String.format("select exists(select film_id from film where film_id = '%s')", id);
        Boolean exist = jdbcTemplate.queryForObject(sql, Boolean.class);
        if (!exist) {
            throw new NotFoundException("Фильм не найден");
        }
        return jdbcTemplate.queryForObject("select f.*, m.name as mpa_name from film f join mpa m on f.mpa_id = m.mpa_id where film_id = ?",
                (rs, rowNum) -> {
                    int filmId = rs.getInt("film_id");
                    return new Film(filmId,
                            rs.getString("name"),
                            rs.getString("description"),
                            rs.getDate("releaseDate").toLocalDate(),
                            rs.getInt("duration"),
                            new Mpa(rs.getInt("mpa_id"), rs.getString("mpa_name")),
                            getFilmGenre(filmId));
                },
                id);
    }

    @Override
    public Film addLike(long id, long userId) {
        String sqlFilm = String.format("select exists(select film_id from film where film_id = '%s')", id);
        Boolean existFilm = jdbcTemplate.queryForObject(sqlFilm, Boolean.class);
        if (!existFilm) {
            throw new NotFoundException("Фильм не найден");
        }

        String sqlUser = String.format("select exists(select user_id from users where user_id = '%s')", userId);
        Boolean existUser = jdbcTemplate.queryForObject(sqlUser, Boolean.class);
        if (!existUser) {
            throw new NotFoundException("Пользователь не найден");
        }

        jdbcTemplate.update("insert into likes values (?, ?)", id, userId);
        return getFilmById(id);
    }

    @Override
    public Film deleteLike(long id, long userId) {
        String sqlFilm = String.format("select exists(select film_id from film where film_id = '%s')", id);
        Boolean existFilm = jdbcTemplate.queryForObject(sqlFilm, Boolean.class);
        if (!existFilm) {
            throw new NotFoundException("Фильм не найден");
        }

        String sqlUser = String.format("select exists(select user_id from users where user_id = '%s')", userId);
        Boolean existUser = jdbcTemplate.queryForObject(sqlUser, Boolean.class);
        if (!existUser) {
            throw new NotFoundException("Пользователь не найден");
        }

        jdbcTemplate.update("delete from likes where film_id = ? and user_id = ?", id, userId);
        return getFilmById(id);
    }

    @Override
    public List<Film> getPopularFilms(int count) {
        return jdbcTemplate.query("select f.*, m.name as mpa_name from film f left join mpa m on f.mpa_id = m.mpa_id " +
                        "left join likes l on f.film_id = l.film_id group by f.film_id order by count(l.user_id) desc limit ?",
                (rs, rowNum) -> {
                    int filmId = rs.getInt("film_id");
                    return new Film(filmId,
                            rs.getString("name"),
                            rs.getString("description"),
                            rs.getDate("releaseDate").toLocalDate(),
                            rs.getInt("duration"),
                            new Mpa(rs.getInt("mpa_id"), rs.getString("mpa_name")),
                            getFilmGenre(filmId));
                }, count);
    }

    public List<Genre> getFilmGenre(long filmId) {
        String query = "select name, fg.genre_id\n" +
                "    from film_genre fg\n" +
                "    join genres g on fg.genre_id = g.genre_id\n" +
                "    where fg.film_id = ?";
        return jdbcTemplate.query(query, (rs, rowNum) -> new Genre(rs.getInt("genre_id"), rs.getString("name")), filmId);

    }

    private void createOrReplaceFilmGenre(Film film) {
        if (film.getGenres() == null) {
            return;
        }
        Map<Integer, Genre> map = new HashMap<>();
        for (Genre g : film.getGenres()) {
            map.put(g.getId(), g);
        }

        film.setGenres(new ArrayList<>(map.values()));
        String sql = "delete from film_genre where film_id = ?";
        jdbcTemplate.update(sql, film.getId());

        List<Map<String, Object>> genre = map.values().stream()
                .map(gen -> Map.<String, Object>of("film_id", film.getId(), "genre_id", gen.getId()))
                .collect(Collectors.toList());

        final SimpleJdbcInsert statement = new SimpleJdbcInsert(jdbcTemplate.getDataSource())
                .withTableName("film_genre")
                .usingColumns("film_id", "genre_id");

        statement.executeBatch(SqlParameterSourceUtils.createBatch(genre));
    }
}
