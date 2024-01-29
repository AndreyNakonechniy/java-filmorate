package ru.yandex.practicum.filmorate.service;

import ru.yandex.practicum.filmorate.model.Mpa;

import java.util.List;

public interface MpaService {
    public List<Mpa> getAllMpa();

    public Mpa getMpaById(int id);
}
