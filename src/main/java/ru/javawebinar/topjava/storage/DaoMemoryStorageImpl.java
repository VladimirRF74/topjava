package ru.javawebinar.topjava.storage;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

import static org.slf4j.LoggerFactory.getLogger;

public class DaoMemoryStorageImpl implements Dao {
    private static final Logger log = getLogger(DaoMemoryStorageImpl.class);
    private final ConcurrentMap<Integer, Meal> mapMeals = new ConcurrentHashMap<>();
    private static final AtomicInteger idCounter = new AtomicInteger(1);

    @Override
    public Meal get(int id) {
        log.debug("get meal from storage");
        return mapMeals.get(id);
    }

    public List<Meal> getAll() {
        log.debug("get list meals from storage");
        return new ArrayList<>(mapMeals.values());
    }

    @Override
    public Meal create(Meal meal) {
        log.debug("create meal");
        Integer id = createID();
        meal.setId(id);
        return mapMeals.putIfAbsent(id, meal);
    }

    @Override
    public void delete(Integer id) {
        log.debug("delete from storage");
        mapMeals.remove(id);
    }

    @Override
    public Meal update(Integer id, Meal newMeal) {
        log.debug("update meal in storage");
        mapMeals.replace(id, newMeal);
        return newMeal;
    }

    private static Integer createID() {
        return idCounter.getAndIncrement();
    }
}