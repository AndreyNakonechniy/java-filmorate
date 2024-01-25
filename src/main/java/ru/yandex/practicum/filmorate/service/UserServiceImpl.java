package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.IncorrectDataException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.InMemoryUserStorage;
import ru.yandex.practicum.filmorate.validation.UserValidation;

import java.util.*;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final InMemoryUserStorage userStorage;

    @Override
    public List<User> showAllUsers() {
        return userStorage.showAllUsers();
    }

    @Override
    public User createUser(User user) {
        UserValidation.validate(user);
        return userStorage.createUser(user);
    }

    @Override
    public User updateUser(User user) {
        UserValidation.validate(user);
        return userStorage.updateUser(user);
    }

    @Override
    public User getUserById(long id) {
        userStorage.checkId(id);
        return getUserStorageMap().get(id);
    }

    @Override
    public User addFriend(long id, long friendId) {
        userStorage.checkId(id);
        userStorage.checkId(friendId);
        if (id == friendId) {
            throw new IncorrectDataException("Некоректная операция");
        }
        if (getUserStorageMap().get(id).getFriendsId() == null) {
            getUserStorageMap().get(id).setFriendsId(new HashSet<>());
        }
        if (getUserStorageMap().get(friendId).getFriendsId() == null) {
            getUserStorageMap().get(friendId).setFriendsId(new HashSet<>());
        }
        getUserStorageMap().get(id).getFriendsId().add(friendId);
        getUserStorageMap().get(friendId).getFriendsId().add(id);
        return getUserStorageMap().get(id);
    }

    @Override
    public User deleteFriend(long id, long friendId) {
        userStorage.checkId(id);
        userStorage.checkId(friendId);
        if (id == friendId) {
            throw new IncorrectDataException("Некоректная операция");
        }
        getUserStorageMap().get(id).getFriendsId().remove(friendId);
        getUserStorageMap().get(friendId).getFriendsId().remove(id);
        return getUserStorageMap().get(id);
    }

    @Override
    public List<User> getFriends(long id) {
        userStorage.checkId(id);
        return getUserStorageMap().get(id).getFriendsId().stream()
                .map(getUserStorageMap()::get)
                .collect(Collectors.toList());
    }

    @Override
    public List<User> getCommonFriends(long id, long otherId) {
        userStorage.checkId(id);
        userStorage.checkId(otherId);
        if (id == otherId) {
            throw new IncorrectDataException("Некоректная операция");
        }
        final User user = getUserStorageMap().get(id);
        final User other = getUserStorageMap().get(otherId);
        final Set<Long> friendsIds = user.getFriendsId();
        final Set<Long> otherFriendsIds = other.getFriendsId();

        if (friendsIds != null) {
            return friendsIds.stream()
                    .filter(otherFriendsIds::contains)
                    .map(getUserStorageMap()::get)
                    .collect(Collectors.toList());
        } else {
            return Collections.emptyList();
        }
    }

    private Map<Long, User> getUserStorageMap() {
        return userStorage.getUsersMap();
    }

}
