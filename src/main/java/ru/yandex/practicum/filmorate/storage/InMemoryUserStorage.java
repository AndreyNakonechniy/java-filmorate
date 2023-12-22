package ru.yandex.practicum.filmorate.storage;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class InMemoryUserStorage implements UserStorage {
    private final Map<Long, User> users = new HashMap<>();
    private long id = 1L;

    @Override
    public List<User> showAllUsers() {
        return new ArrayList<>(users.values());
    }

    @Override
    public User createUser(User user) {
        user.setId(id++);
        user.setFriendsId(new HashSet<>());
        users.put(user.getId(), user);
        return user;
    }

    @Override
    public User updateUser(User user) {
        if (!users.containsKey(user.getId())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Пользователь не найден");
        }
        users.put(user.getId(), user);
        return user;
    }

    @Override
    public User getUserById(long id) {
        if (!users.containsKey(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Пользователь не найден");
        }
        return users.get(id);
    }

    @Override
    public User addFriend(long id, long friendId) {
        if (!users.containsKey(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Пользователь не найден");
        }
        if (!users.containsKey(friendId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Пользователь не найден");
        }
        if (id == friendId) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Нельзя добавить себя в друзья");
        }
        if (users.get(id).getFriendsId() == null) {
            users.get(id).setFriendsId(new HashSet<>());
        }
        if (users.get(friendId).getFriendsId() == null) {
            users.get(friendId).setFriendsId(new HashSet<>());
        }
        users.get(id).getFriendsId().add(friendId);
        users.get(friendId).getFriendsId().add(id);
        return users.get(id);
    }

    @Override
    public User deleteFriend(long id, long friendId) {
        if (!users.containsKey(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Пользователь не найден");
        }
        if (!users.containsKey(friendId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Пользователь не найден");
        }
        if (id == friendId) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Некоректная операция");
        }
        users.get(id).getFriendsId().remove(friendId);
        users.get(friendId).getFriendsId().remove(id);
        return users.get(id);
    }

    @Override
    public List<User> getFriends(long id) {
        if (!users.containsKey(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Пользователь не найден");
        }
        return users.get(id).getFriendsId().stream()
                .map(users::get)
                .collect(Collectors.toList());
    }

    @Override
    public List<User> getCommonFriends(long id, long otherId) {
        if (!users.containsKey(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Пользователь не найден");
        }
        if (!users.containsKey(otherId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Пользователь не найден");
        }
        if (id == otherId) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Некоректная операция");
        }
        Set<Long> friendsIds = users.get(id).getFriendsId();
        if (friendsIds != null) {
            return friendsIds.stream().filter(friendsId -> {
                                if (users.get(otherId).getFriendsId() != null) {
                                    return users.get(otherId).getFriendsId().contains(friendsId);
                                } else {
                                    return false;
                                }
                            }
                    )
                    .map(users::get)
                    .collect(Collectors.toList());
        } else {
            return Collections.emptyList();
        }
    }

    @Override
    public boolean checkId(long userId) {
        if (!users.containsKey(userId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Пользователь не найден");
        }
        return true;
    }
}
