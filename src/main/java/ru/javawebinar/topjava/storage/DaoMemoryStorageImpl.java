package ru.javawebinar.topjava.storage;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class DaoMemoryStorageImpl implements Dao {
    private final List<Meal> meals = new CopyOnWriteArrayList<>();

    @Override
    public int size() {
        return meals.size();
    }

    @Override
    public Meal get(int id) {
        return meals.get(isExist(id));
    }

    public List<Meal> getAll() {
        return meals;
    }

    @Override
    public void save(Meal meal) {
        meals.add(meal);
    }

    @Override
    public void delete(Integer id) {
        meals.remove(isExist(id));
    }

    @Override
    public void clear() {
        meals.clear();
    }

    @Override
    public void update(Integer id, Meal meal) {
        meals.set(isExist(id), meal);
    }

    private int isExist(Integer id) {
        for (int i = 0; i < meals.size(); i++) {
            if (meals.get(i).getId().equals(id)) {
                return i;
            }
        }
        return -1;
    }
}