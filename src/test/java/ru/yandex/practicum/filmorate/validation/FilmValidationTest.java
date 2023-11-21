package ru.yandex.practicum.filmorate.validation;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContextException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;


import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class FilmValidationTest {
    String longString = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Phasellus scelerisque, est ac vestibulum" +
            " auctor, orci ex pellentesque augue, eu aliquam enim dolor et odio. Aliquam erat volutpat. Sed metus lectus, " +
            "vulputate sit amet feugiat sit amet, consectetur quis risus. Nam rutrum viverra leo vel ultricies. Phasellus " +
            "luctus massa in vestibulum ultrices. Praesent porta purus lacinia condimentum viverra. Ut quis mauris vel eros " +
            "suscipit vulputate eget ut tortor. Etiam sed quam ac risus dapibus aliquam. In volutpat eros in enim auctor " +
            "maximus et non neque. Phasellus ac ligula varius, finibus lacus quis, dapibus augue. Phasellus sagittis accumsan " +
            "velit ac posuere. Nulla ultricies, ante in iaculis suscipit, nulla massa porttitor dui, ac finibus justo magna ut " +
            "elit. Etiam ante purus, ultricies in blandit ut, ornare vel leo. Proin viverra pharetra nulla in fermentum.";

    Film film;
    Film filmWithWrongDate;
    Film filmWithWrongName;
    Film filmWithWrongDescription;
    Film filmWithWrongDuration;


    @BeforeEach
    void setUp() {
        film = new Film(1, "BackToTheFuture", "filmDescription", LocalDate.of(1985, 7, 3), 116);
        filmWithWrongDate = new Film(1, "BackToTheFuture", "filmDescription", LocalDate.of(1717, 7, 3), 116);
        filmWithWrongName = new Film(1, "", "filmDescription", LocalDate.of(1985, 7, 3), 116);
        filmWithWrongDescription = new Film(1, "BackToTheFuture", longString, LocalDate.of(1985, 7, 3), 116);
        filmWithWrongDuration = new Film(1, "BackToTheFuture", "filmDescription", LocalDate.of(1985, 7, 3), 0);

    }

    @Test
    void filmValidation() {
        Assertions.assertDoesNotThrow(() -> FilmValidation.validate(film));

        ValidationException exceptionTime = Assertions.assertThrows(ValidationException.class, () -> FilmValidation.validate(filmWithWrongDate));
        assertEquals("Дата не может быть раньше 28 декабря 1985 года", exceptionTime.getMessage());

        ValidationException exceptionName = Assertions.assertThrows(ValidationException.class, () -> FilmValidation.validate(filmWithWrongName));
        assertEquals("Имя не может быть пустым", exceptionName.getMessage());

        ValidationException exceptionDescription = Assertions.assertThrows(ValidationException.class, () -> FilmValidation.validate(filmWithWrongDescription));
        assertEquals("Описание не может быть длиннее 200 символов", exceptionDescription.getMessage());

        ValidationException exceptionDuration = Assertions.assertThrows(ValidationException.class, () -> FilmValidation.validate(filmWithWrongDuration));
        assertEquals("Длительность фильма должна быть положительной", exceptionDuration.getMessage());
    }
}