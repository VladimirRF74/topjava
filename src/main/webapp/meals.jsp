<%@ page import="ru.javawebinar.topjava.util.TimeUtil" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <title>Meals list</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Meals</h2>
<div>
    <a href="meals?action=add">
        <p>Add Meal</p>
    </a>
</div>
<section>
    <table class="table" cellpadding="10" border="2px">
        <tr bgcolor="#f5f5dc">
            <th>Date</th>
            <th>Description</th>
            <th>Calories</th>
            <th></th>
            <th></th>
        </tr>

        <c:forEach var="meal" items="${meals}">
            <jsp:useBean id="meal" type="ru.javawebinar.topjava.model.MealTo"/>
            <c:if test="${!meal.excess}">
                <c:set var="color" value="red"/>
            </c:if>
            <c:if test="${meal.excess}">
                <c:set var="color" value="green"/>
            </c:if>
            <tr class="${color}">
                <td hidden>${meal.id}</td>
                <td>${TimeUtil.format(meal.dateTime)}</td>
                <td>${meal.description}</td>
                <td>${meal.calories}</td>
                <td><a href="meals?action=update&&id=${meal.id}">Update</a></td>
                <td><a href="meals?action=delete&&id=${meal.id}">Delete</a></td>
            </tr>
        </c:forEach>
    </table>
</section>
</body>
</html>
