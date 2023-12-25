package ru.yandex.practicum.filmorate.exception;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class ErrorMessage {

   private final String message;
}
