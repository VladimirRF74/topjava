package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.storage.Dao;
import ru.javawebinar.topjava.storage.DaoMemoryStorageImpl;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;
import java.util.Objects;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);
    private final Dao storage = new DaoMemoryStorageImpl();

    {
        storage.save(new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500));
        storage.save(new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000));
        storage.save(new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500));
        storage.save(new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100));
        storage.save(new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000));
        storage.save(new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500));
        storage.save(new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410));

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("redirect to meals");

        String action = request.getParameter("action");
        List<MealTo> meals = MealsUtil.doMealTo((DaoMemoryStorageImpl) storage);
        if (action == null) {
            request.setAttribute("meals", meals);
            request.getRequestDispatcher("/meals.jsp").forward(request, response);
            return;
        }

        switch (Objects.requireNonNull(action)) {
            case "delete":
                log.debug("delete and redirect");
                int id = Integer.parseInt(request.getParameter("id"));
                storage.delete(id);
                response.sendRedirect("meals");
                return;
            case "update":
                log.debug("update and redirect");
                id = Integer.parseInt(request.getParameter("id"));
                Meal meal = storage.get(id);
                request.setAttribute("meal", meal);
                request.getRequestDispatcher("/edit.jsp").forward(request, response);
                break;
            case "add":
                log.debug("add and redirect");
                break;
            default:
                throw new IllegalStateException("Action" + action + "is illegal");
        }
        request.setAttribute("meals", meals);
        request.getRequestDispatcher(("add".equals(action) ? "/edit.jsp" : "/meals.jsp")).forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        log.debug("to form");
        request.setCharacterEncoding("UTF-8");
        String id = request.getParameter("id");
        LocalDateTime dateTime = LocalDateTime.parse(request.getParameter("dateTime"));
        String description = request.getParameter("description");
        int calories = Integer.parseInt(request.getParameter("calories"));

        if (id == null || id.isEmpty()) {
            Meal meal = storage.create(dateTime, description, calories);
            storage.save(meal);
        } else {
            storage.update(Integer.valueOf(id), dateTime, description, calories);
        }
        response.sendRedirect("meals");
    }
}
