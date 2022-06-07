package ru.javawebinar.topjava.storage;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.web.MealServlet;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import static org.slf4j.LoggerFactory.getLogger;

public class DaoMemoryStorageImpl implements Dao {
    private static final Logger log = getLogger(DaoMemoryStorageImpl.class);
    private final ConcurrentHashMap<Integer, Meal> mapMeals = new ConcurrentHashMap<>();

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
    public Meal create(LocalDateTime dateTime, String description, int calories) {
        log.debug("create meal");
        return new Meal(dateTime, description, calories);
    }

    @Override
    public void save(Meal meal) {
        log.debug("save to storage");
        mapMeals.put(meal.getId(), meal);
    }

    @Override
    public void delete(Integer id) {
        log.debug("delete from storage");
        mapMeals.remove(id);
    }

    @Override
    public Meal update(Integer id, LocalDateTime dateTime, String description, int calories) {
        log.debug("update meal in storage");
        Meal meal = mapMeals.get(id);
        meal.setDateTime(dateTime);
        meal.setDescription(description);
        meal.setCalories(calories);
        mapMeals.replace(id, meal);
        return meal;
    }
}