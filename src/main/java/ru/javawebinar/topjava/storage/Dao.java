package ru.javawebinar.topjava.storage;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;

public interface Dao {

    int size();

    Meal get(int id);

    List<Meal> getAll();

    void save(Meal meal);

    void delete(Integer id);

    void clear();

    void update(Integer id, Meal meal);
}