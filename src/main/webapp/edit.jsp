<%@ page import="ru.javawebinar.topjava.util.TimeUtil" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Edit meal</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Edit meals</h2>
<form method="post" action="meals" enctype="application/x-www-form-urlencoded">
    <label>
        <input class="field" hidden type="number" name="id" value="${meal.id}">
    </label>
    <div>
        <p><b>DateTime:</b>
            <label>
                <input class="field" type="datetime-local" name="dateTime" value="${meal.dateTime}" required>
            </label>
        </p>
    </div>
    <div>
        <p><b>Description:</b>
            <label>
                <input class="field" type="text" name="description" size=40 placeholder="Description" value="${meal.description}" required>
            </label>
        </p>
    </div>
    <div>
        <p><b>Calories:</b>
            <label>
                <input class="field" type="number" name="calories" size=40 placeholder="Calories" value="${meal.calories}" required>
            </label>
        </p>
    </div>
    <p><input type="submit" value="Save">
        <input type="button" onclick="window.history.back()" value="Cancel"></p>
</form>
</body>
</html>
