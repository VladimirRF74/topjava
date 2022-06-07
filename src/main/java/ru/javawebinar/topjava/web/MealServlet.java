package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.MealTestData;
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
import java.util.List;
import java.util.Objects;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(UserServlet.class);
    Dao storage = MealTestData.create();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("redirect to users");

        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");
        List<MealTo> meals = MealsUtil.doMealTo((DaoMemoryStorageImpl) storage);
        if (action == null) {
            request.setAttribute("meals", meals);
            request.getRequestDispatcher("/meals.jsp").forward(request, response);
            return;
        }

        switch (Objects.requireNonNull(action)) {
            case "delete":
                int id = Integer.parseInt(request.getParameter("id"));
                storage.delete(id);
                response.sendRedirect("meals");
                return;
            case "update":
                id = Integer.parseInt(request.getParameter("id"));
                Meal meal = storage.get(id);
                request.setAttribute("meal", meal);
                request.getRequestDispatcher("/edit.jsp").forward(request, response);
                break;
            case "add":
                break;
            default:
                throw new IllegalStateException("Action" + action + "is illegal");
        }
        request.setAttribute("meals", meals);
        request.getRequestDispatcher(("add".equals(action) ? "/edit.jsp" : "/meals.jsp")).forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        request.setCharacterEncoding("UTF-8");
        String id = request.getParameter("id");
        if (id == null || id.isEmpty()) {
            storage.save(new Meal(LocalDateTime.parse(request.getParameter("dateTime")), request.getParameter("description"),
                    Integer.parseInt(request.getParameter("calories"))));
        } else {
            storage.update(Integer.valueOf(id), new Meal(LocalDateTime.parse(request.getParameter("dateTime")), request.getParameter("description"),
                    Integer.parseInt(request.getParameter("calories"))));
        }
        request.setAttribute("meals", MealsUtil.doMealTo((DaoMemoryStorageImpl) storage));
        request.getRequestDispatcher("/meals.jsp").forward(request, response);
    }
}
