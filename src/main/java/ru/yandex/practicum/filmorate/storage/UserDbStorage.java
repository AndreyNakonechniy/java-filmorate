package ru.yandex.practicum.filmorate.storage;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.batch.BatchProperties;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Component
public class UserDbStorage implements UserStorage {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<User> showAllUsers() {
        return jdbcTemplate.query("select * from users", (rs, rowNum) -> new User(rs.getInt("user_id"),
                rs.getString("email"), rs.getString("login"),
                rs.getString("name"), rs.getDate("birthday").toLocalDate()));
    }

    @Override
    public User createUser(User user) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate.getDataSource())
                .withTableName("users")
                .usingGeneratedKeyColumns("user_id");
        Map<String, Object> params = Map.of("email", user.getEmail(), "login", user.getLogin(), "name", user.getName(),
                "birthday", user.getBirthday());
        Number id = simpleJdbcInsert.executeAndReturnKey(params);
        user.setId(id.intValue());
        return user;
    }

    @Override
    public User updateUser(User user) {
        String sql = String.format("select exists(select user_id from users where user_id = '%s')", user.getId());
        Boolean exist = jdbcTemplate.queryForObject(sql, Boolean.class);
        if (!exist) {
            throw new NotFoundException("Пользователь не найден");
        }

        jdbcTemplate.update("update users set email = ?, login = ?, name = ?, birthday = ? where user_id = ?",
                user.getEmail(), user.getLogin(), user.getName(), user.getBirthday(), user.getId());
        return user;
    }

    @Override
    public User getUserById(long id) {
        String sql = String.format("select exists(select user_id from users where user_id = '%s')", id);
        Boolean exist = jdbcTemplate.queryForObject(sql, Boolean.class);
        if (!exist) {
            throw new NotFoundException("Пользователь не найден");
        }

        return jdbcTemplate.queryForObject("select * from users where user_id = ?", (rs, rowNum) -> new User(rs.getInt("user_id"),
                        rs.getString("email"), rs.getString("login"),
                        rs.getString("name"), rs.getDate("birthday").toLocalDate()),
                id);
    }

    @Override
    public User addFriend(long id, long friendId) {
        String sqlUser = String.format("select exists(select user_id from users where user_id = '%s')", id);
        Boolean existUser = jdbcTemplate.queryForObject(sqlUser, Boolean.class);
        if (!existUser) {
            throw new NotFoundException("Пользователь не найден");
        }
        String sqlFriend = String.format("select exists(select user_id from users where user_id = '%s')", friendId);
        Boolean existFriend = jdbcTemplate.queryForObject(sqlFriend, Boolean.class);
        if (!existFriend) {
            throw new NotFoundException("Пользователь не найден");
        }

        jdbcTemplate.update("insert into friend values (?, ?)", id, friendId);

        return getUserById(id);
    }

    @Override
    public User deleteFriend(long id, long friendId) {
        String sqlUser = String.format("select exists(select user_id from users where user_id = '%s')", id);
        Boolean existUser = jdbcTemplate.queryForObject(sqlUser, Boolean.class);
        if (!existUser) {
            throw new NotFoundException("Пользователь не найден");
        }
        String sqlFriend = String.format("select exists(select user_id from users where user_id = '%s')", friendId);
        Boolean existFriend = jdbcTemplate.queryForObject(sqlFriend, Boolean.class);
        if (!existFriend) {
            throw new NotFoundException("Пользователь не найден");
        }

        jdbcTemplate.update("delete from friend where user_id = ? and friend_id = ?", id,friendId);
        return getUserById(id);
    }

    @Override
    public List<User> getFriends(long id) {
        String sqlUser = String.format("select exists(select user_id from users where user_id = '%s')", id);
        Boolean existUser = jdbcTemplate.queryForObject(sqlUser, Boolean.class);
        if (!existUser) {
            throw new NotFoundException("Пользователь не найден");
        }
       return jdbcTemplate.query("select * from users where user_id in " +
                "(select friend_id from friend where user_id = ?)",(rs, rowNum) -> new User(rs.getInt("user_id"),
                rs.getString("email"), rs.getString("login"),
                rs.getString("name"), rs.getDate("birthday").toLocalDate()), id);
    }

    @Override
    public List<User> getCommonFriends(long id, long otherId) {
        String sqlUser = String.format("select exists(select user_id from users where user_id = '%s')", id);
        Boolean existUser = jdbcTemplate.queryForObject(sqlUser, Boolean.class);
        if (!existUser) {
            throw new NotFoundException("Пользователь не найден");
        }
        String sqlOther = String.format("select exists(select user_id from users where user_id = '%s')", otherId);
        Boolean existOther = jdbcTemplate.queryForObject(sqlOther, Boolean.class);
        if (!existOther) {
            throw new NotFoundException("Пользователь не найден");
        }

        return jdbcTemplate.query("select * from users where user_id in " +
                "(select friend_id from friend where user_id = ?) and user_id in" +
                "(select friend_id from friend where user_id = ?)",(rs, rowNum) -> new User(rs.getInt("user_id"),
                rs.getString("email"), rs.getString("login"),
                rs.getString("name"), rs.getDate("birthday").toLocalDate()), id,otherId);
    }

//    @Override
//    public boolean checkId(long userId) {
//        return false;
//    }
}
