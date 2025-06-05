<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Фильмы</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 0; padding: 0; }
        .header { background-color: #343a40; color: white; padding: 15px; display: flex; justify-content: space-between; align-items: center; }
        .nav-links a { color: white; text-decoration: none; margin-left: 15px; }
        .container { padding: 20px; }
        .logout-btn { background: none; border: none; color: white; cursor: pointer; font-size: 16px; }
        table { width: 100%; border-collapse: collapse; margin-top: 20px; }
        th, td { border: 1px solid #ddd; padding: 8px; text-align: left; }
        th { background-color: #f2f2f2; }
        tr:nth-child(even) { background-color: #f9f9f9; }
        .action-form { display: inline; }
        .add-film-form { margin-top: 30px; padding: 20px; background-color: #f5f5f5; border-radius: 5px; }
        .form-group { margin-bottom: 15px; }
        label { display: block; margin-bottom: 5px; }
        input, select { width: 100%; padding: 8px; border: 1px solid #ddd; border-radius: 4px; box-sizing: border-box; }
        button { padding: 8px 15px; background-color: #007bff; color: white; border: none; border-radius: 4px; cursor: pointer; }
        button:hover { background-color: #0056b3; }
        .error { color: red; margin-top: 10px; }
    </style>
</head>
<body>
<div class="header">
    <h1>Кинотеатр - Фильмы</h1>
    <div class="nav-links">
        <a href="${pageContext.request.contextPath}/">Главная</a>
        <form action="${pageContext.request.contextPath}/logout" method="post" style="display: inline;">
            <button type="submit" class="logout-btn">Выйти</button>
        </form>
    </div>
</div>

<div class="container">
    <c:if test="${not empty error}">
        <div class="error">${error}</div>
    </c:if>

    <h2>Список фильмов</h2>
    <table>
        <thead>
        <tr>
            <th>Название</th>
            <th>Жанр</th>
            <th>Длительность (мин)</th>
            <th>Сценарист</th>
            <th>Рейтинг</th>
            <th>Действия</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${films}" var="film">
            <tr>
                <td>${film.name}</td>
                <td>${film.genre}</td>
                <td>${film.duration}</td>
                <td>${film.screenWriter}</td>
                <td>${film.rating}</td>
                <td>
                    <form class="action-form" action="${pageContext.request.contextPath}/films" method="post">
                        <input type="hidden" name="action" value="delete">
                        <input type="hidden" name="filmId" value="${film.filmId}">
                        <button type="submit">Удалить</button>
                    </form>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

    <div class="add-film-form">
        <h3>Добавить новый фильм</h3>
        <form action="${pageContext.request.contextPath}/films" method="post">
            <input type="hidden" name="action" value="add">
            <div class="form-group">
                <label for="name">Название:</label>
                <input type="text" id="name" name="name" required>
            </div>
            <div class="form-group">
                <label for="genre">Жанр:</label>
                <input type="text" id="genre" name="genre" required>
            </div>
            <div class="form-group">
                <label for="duration">Длительность (мин):</label>
                <input type="number" id="duration" name="duration" required>
            </div>
            <div class="form-group">
                <label for="screenWriter">Сценарист:</label>
                <input type="text" id="screenWriter" name="screenWriter" required>
            </div>
            <div class="form-group">
                <label for="rating">Рейтинг:</label>
                <input type="number" step="0.1" id="rating" name="rating" required>
            </div>
            <button type="submit">Добавить фильм</button>
        </form>
    </div>
</div>
</body>
</html>