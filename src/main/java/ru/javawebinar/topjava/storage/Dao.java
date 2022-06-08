package ru.javawebinar.topjava.storage;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;

public interface Dao {

    Meal get(int id);

    List<Meal> getAll();

    Meal create(Meal meal);

//    void create(Meal meal);

    void delete(Integer id);

    Meal update(Integer id, Meal newMeal);
}
