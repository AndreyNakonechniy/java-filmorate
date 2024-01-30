package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.MpaStorage;

import java.util.List;

@RequiredArgsConstructor
@Service
public class MpaServiceIml implements MpaService {
    private final MpaStorage mpaStorage;

    @Override
    public List<Mpa> getAllMpa() {
        return mpaStorage.getAllMpa();
    }

    @Override
    public Mpa getMpaById(int id) {
        return mpaStorage.getMpaById(id);
    }
}
