package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.storage.DaoMemoryStorageImpl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static ru.javawebinar.topjava.util.TimeUtil.isBetweenHalfOpen;

public class MealsUtil {
    private static final int CALORIES_PER_DAY = 2000;

    public static List<MealTo> filteredByStreams(List<Meal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {

        return meals.stream()
                .filter(meal -> isBetweenHalfOpen(meal.getTime(), startTime, endTime))
                .map(meal -> createTo(meal, caloriesSumByDay(meals).get(meal.getDate()) > caloriesPerDay))
                .collect(Collectors.toList());
    }

    private static Map<LocalDate, Integer> caloriesSumByDay(List<Meal> meals) {
        return meals.stream()
                .collect(
                        Collectors.groupingBy(Meal::getDate, Collectors.summingInt(Meal::getCalories))
//                      Collectors.toMap(Meal::getDate, Meal::getCalories, Integer::sum)
                );
    }

    private static MealTo createTo(Meal meal, boolean excess) {
        return new MealTo(meal.getId(), meal.getDateTime(), meal.getDescription(), meal.getCalories(), excess);
    }

    public static List<MealTo> doMealTo(DaoMemoryStorageImpl var) {

        List<MealTo> result = new ArrayList<>();
        for (Meal meal : var.getAll()) {
            LocalDateTime dateTime = meal.getDateTime();

            result.add(new MealTo(meal.getId(), meal.getDateTime(), meal.getDescription(),
                    meal.getCalories(), caloriesSumByDay(var.getAll()).get(dateTime.toLocalDate()) <= CALORIES_PER_DAY));
        }
        return result;
    }

    private static final AtomicInteger idCounter = new AtomicInteger(1);

    public static Integer createID() {
        return idCounter.getAndIncrement();
    }
}