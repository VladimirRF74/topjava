package ru.javawebinar.topjava.storage;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.List;

public interface Dao {

    Meal get(int id);

    List<Meal> getAll();

    Meal create(LocalDateTime dateTime, String description, int calories);

    void save(Meal meal);

    void delete(Integer id);

    Meal update(Integer id, LocalDateTime dateTime, String description, int calories);
}