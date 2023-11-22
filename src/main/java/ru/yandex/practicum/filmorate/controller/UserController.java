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

    private Map<Integer, User> users = new HashMap<>();
    private int id = 1;

    @GetMapping("/users")
    public List<User> showAllUsers() {
        return new ArrayList<>(users.values());
    }

    @PostMapping("/users")
    public User createUser(@RequestBody User user) {
        log.info("Получен запрос POST /users.");
        UserValidation.validate(user);
        user.setId(id);
        id++;
        users.put(user.getId(), user);
        return user;
    }

    @PutMapping("/users")
    public User updateUser(@RequestBody User user) {
        log.info("Получен запрос PUT /users.");
        UserValidation.validate(user);
        if (!users.containsKey(user.getId())){
            throw new ValidationException();
        }
        users.put(user.getId(), user);
        return user;
    }
}
