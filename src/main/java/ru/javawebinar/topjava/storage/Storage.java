package ru.javawebinar.topjava.storage;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;

public interface Storage {

    Meal get();

    List<Meal> getAll();

    void save();

    void delete();

    void clear();

    void update();
}