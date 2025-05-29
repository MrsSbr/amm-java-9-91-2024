<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Главная</title>
</head>
<body>
<h2>Добро пожаловать, <%= session.getAttribute("login") %>!</h2>

<form action="scooters" method="get">
    <button type="submit">Начать поездку</button>
</form>

<br>

<form action="finishTrip" method="get">
    <button type="submit">Закончить поездку</button>
</form>

<br>

<form action="finishedTrips" method="get">
    <button type="submit">Завершённые поездки</button>
</form>

<br>

<form action="index.jsp" method="get">
    <button type="submit">Вернуться назад</button>
</form>
</body>
</html>
