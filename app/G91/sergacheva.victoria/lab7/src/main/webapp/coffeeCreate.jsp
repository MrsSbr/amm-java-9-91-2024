<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Добавить кофе</title>
</head>
<body>
<h1>Добавить кофе</h1>
<form action="/coffees/create" method="post">
    <label for="title">Название:</label>
    <input type="text" id="title" name="title" required>
    <br>
    <label for="description">Описание:</label>
    <textarea id="description" name="description" required></textarea>
    <br>
    <button type="submit">Создать</button>
</form>
</body>
</html>
