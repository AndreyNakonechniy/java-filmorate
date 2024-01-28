//package ru.yandex.practicum.filmorate.storage;
//
//import org.springframework.stereotype.Component;
//import ru.yandex.practicum.filmorate.exception.NotFoundException;
//import ru.yandex.practicum.filmorate.model.User;
//
//import java.util.*;
//
//@Component
//public class InMemoryUserStorage implements UserStorage {
//    private final Map<Long, User> users = new HashMap<>();
//    private long id = 1L;
//
//    @Override
//    public List<User> showAllUsers() {
//        return new ArrayList<>(users.values());
//    }
//
//    @Override
//    public User createUser(User user) {
//        user.setId(id++);
//        user.setFriendsId(new HashSet<>());
//        users.put(user.getId(), user);
//        return user;
//    }
//
//    @Override
//    public User updateUser(User user) {
//        checkId(user.getId());
//        users.put(user.getId(), user);
//        return user;
//    }
//
//    @Override
//    public boolean checkId(long userId) {
//        if (!users.containsKey(userId)) {
//            throw new NotFoundException("Пользователь не найден");
//        }
//        return true;
//    }
//
//    public Map<Long, User> getUsersMap() {
//        return users;
//    }
//}
