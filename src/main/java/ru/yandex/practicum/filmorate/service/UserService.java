package ru.yandex.practicum.filmorate.service;

import ru.yandex.practicum.filmorate.model.User;

import java.util.List;
import java.util.Set;

public interface UserService {
    public List<User> showAllUsers();

    public User createUser(User user);

    public User updateUser(User user);

    public User getUserById(long id);

    public User addFriend(long id, long friendId);

    public User deleteFriend(long id, long friendId);

    public List<User> getFriends(long id);

    public List<User> getCommonFriends(long id, long otherId);
}
