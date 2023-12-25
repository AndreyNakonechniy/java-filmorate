package ru.yandex.practicum.filmorate.validation;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

class UserValidationTest {

    User user;
    User userWithoutName;
    User userWithWrongEmail;
    User userWithEmptyEmail;
    User userWithWrongLogin;
    User userWithEmptyLogin;
    User userWithWrongDate;

    @BeforeEach
    void setUp() {
        user = new User(1, "email@email", "login", "name", LocalDate.of(2000, 7, 30), new HashSet<>());
        userWithoutName = new User(1, "email@email", "login", "", LocalDate.of(2000, 7, 30), new HashSet<>());
        userWithWrongEmail = new User(1, "emailemail", "login", "name", LocalDate.of(2000, 7, 30), new HashSet<>());
        userWithEmptyEmail = new User(1, "", "login", "name", LocalDate.of(2000, 7, 30), new HashSet<>());
        userWithWrongLogin = new User(1, "email@email", "log in", "name", LocalDate.of(2000, 7, 30), new HashSet<>());
        userWithEmptyLogin = new User(1, "email@email", "", "name", LocalDate.of(2000, 7, 30), new HashSet<>());
        userWithWrongDate = new User(1, "email@email", "login", "name", LocalDate.of(2077, 7, 30), new HashSet<>());
    }

    @Test
    void userValidation() {
        Assertions.assertDoesNotThrow(() -> UserValidation.validate(user));
        Assertions.assertDoesNotThrow(() -> UserValidation.validate(userWithoutName));

        ValidationException exceptionEmail = Assertions.assertThrows(ValidationException.class, () -> UserValidation.validate(userWithWrongEmail));
        assertEquals("Почта должна содержать символ \"@\" и не быть пустым", exceptionEmail.getMessage());
        ValidationException exceptionEmptyEmail = Assertions.assertThrows(ValidationException.class, () -> UserValidation.validate(userWithEmptyEmail));
        assertEquals("Почта должна содержать символ \"@\" и не быть пустым", exceptionEmptyEmail.getMessage());

        ValidationException exceptionLogin = Assertions.assertThrows(ValidationException.class, () -> UserValidation.validate(userWithWrongLogin));
        assertEquals("Логин не может быть пустым и не должен содержать пробелы", exceptionLogin.getMessage());
        ValidationException exceptionEmptyLogin = Assertions.assertThrows(ValidationException.class, () -> UserValidation.validate(userWithEmptyLogin));
        assertEquals("Логин не может быть пустым и не должен содержать пробелы", exceptionEmptyLogin.getMessage());

        ValidationException exceptionDate = Assertions.assertThrows(ValidationException.class, () -> UserValidation.validate(userWithWrongDate));
        assertEquals("Дата рождения не может быть в будущем", exceptionDate.getMessage());
    }
}