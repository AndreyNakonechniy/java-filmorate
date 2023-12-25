package ru.yandex.practicum.filmorate.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.yandex.practicum.filmorate.exception.ErrorMessage;
import ru.yandex.practicum.filmorate.exception.IncorrectDataException;
import ru.yandex.practicum.filmorate.exception.NotFoundException;

@RestControllerAdvice
public class ErrorHandler {
    @ExceptionHandler(IncorrectDataException.class)
    public ResponseEntity<ErrorMessage> incorrectDataExceptionHandler(IncorrectDataException e) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorMessage(e.getMessage()));
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorMessage> notFoundExceptionHandler(NotFoundException e) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ErrorMessage(e.getMessage()));
    }
}
