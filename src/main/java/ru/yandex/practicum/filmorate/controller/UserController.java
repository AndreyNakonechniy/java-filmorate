package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.validation.UserValidation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
public class UserController {

    private final Map<Long, User> users = new HashMap<>();
    private long id = 1L;

    @GetMapping("/users")
    public List<User> showAllUsers() {
        return new ArrayList<>(users.values());
    }

    @PostMapping("/users")
    public User createUser(@RequestBody User user) {
        log.info("Creating user {}", user);
        UserValidation.validate(user);
        user.setId(id++);
        users.put(user.getId(), user);
        return user;
    }

    @PutMapping("/users")
    public User updateUser(@RequestBody User user) {
        log.info("Updating user {}", user);
        UserValidation.validate(user);
        if (!users.containsKey(user.getId())) {
            throw new ValidationException();
        }
        users.put(user.getId(), user);
        return user;
    }
}
