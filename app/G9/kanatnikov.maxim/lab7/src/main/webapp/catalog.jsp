<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Catalog</title>
    <style>
        table { border-collapse: collapse; width: 100%; }
        th, td { padding: 8px; text-align: left; border-bottom: 1px solid #ddd; }
        tr:hover { background-color: #f5f5f5; }
    </style>
</head>
<body>
<h1>Добро пожаловать, ${user.firstName}!</h1>
<a href="/logout">Выйти</a>

<h2>Каталог настольных игр:</h2>

<c:choose>
    <c:when test="${empty games}">
        <p>Игр пока нет в каталоге</p>
    </c:when>
    <c:otherwise>
        <table>
            <tr>
                <th>Название</th>
                <th>Цена (руб)</th>
                <th>Жанр</th>
                <th>Мин. возраст</th>
                <th>Издатель</th>
                <th>Описание</th>
            </tr>
            <c:forEach items="${games}" var="game">
                <tr>
                    <td>${game.name}</td>
                    <td>${game.price}</td>
                    <td>${game.genre}</td>
                    <td>от ${game.minAge} лет</td>
                    <td>${game.publisher}</td>
                    <td>${game.description}</td>
                </tr>
            </c:forEach>
        </table>
    </c:otherwise>
</c:choose>
</body>
</html>