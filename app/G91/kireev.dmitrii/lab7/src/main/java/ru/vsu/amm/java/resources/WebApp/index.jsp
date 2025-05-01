<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Фильмы</title>
</head>
<body>
<h1>Доступные фильмы</h1>
<table border="1">
    <thead>
    <tr>
        <th>Название</th>
        <th>Жанр</th>
        <th>Продолжительность</th>
        <th>Рейтинг</th>
        <th>Купить билет</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="film" items="${films}">
        <tr>
            <td>${film.name}</td>
            <td>${film.genre}</td>
            <td>${film.duration} мин</td>
            <td>${film.rating}</td>
            <td><a href="ticket.jsp?filmId=${film.filmId}">Купить билет</a></td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</body>
</html>