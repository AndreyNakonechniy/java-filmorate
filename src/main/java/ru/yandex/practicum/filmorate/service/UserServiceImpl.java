package ru.yandex.practicum.filmorate.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;
import ru.yandex.practicum.filmorate.validation.UserValidation;

import java.util.List;
import java.util.Set;

@Slf4j
@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private UserStorage userStorage;

    @Override
    public List<User> showAllUsers() {
        return userStorage.showAllUsers();
    }

    @Override
    public User createUser(User user) {
        UserValidation.validate(user);
        log.info("Creating user {}", user);
        return userStorage.createUser(user);
    }

    @Override
    public User updateUser(User user) {
        UserValidation.validate(user);
        log.info("Updating user {}", user);
        return userStorage.updateUser(user);
    }

    @Override
    public User getUserById(long id) {
        log.info("Getting user by id {}", id);
        return userStorage.getUserById(id);
    }

    @Override
    public User addFriend(long id, long friendId) {
        log.info("Adding friend {}", friendId);
        return userStorage.addFriend(id, friendId);
    }

    @Override
    public User deleteFriend(long id, long friendId) {
        log.info("Deleting friend {}", friendId);
        return userStorage.deleteFriend(id, friendId);
    }

    @Override
    public List<User> getFriends(long id) {
        log.info("Getting friends {}", id);
        return userStorage.getFriends(id);
    }

    @Override
    public List<User> getCommonFriends(long id, long otherId) {
        log.info("Getting common friends {}", id);
        return userStorage.getCommonFriends(id, otherId);
    }
}
