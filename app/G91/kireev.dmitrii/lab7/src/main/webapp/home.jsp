<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Главная страница</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 0; padding: 0; }
        .header { background-color: #343a40; color: white; padding: 15px; display: flex; justify-content: space-between; align-items: center; }
        .nav-links a { color: white; text-decoration: none; margin-left: 15px; }
        .container { padding: 20px; }
        .welcome { margin-bottom: 20px; }
        .logout-btn { background: none; border: none; color: white; cursor: pointer; font-size: 16px; }
    </style>
</head>
<body>
<div class="header">
    <h1>Кинотеатр</h1>
    <div class="nav-links">
        <a href="${pageContext.request.contextPath}/films">Фильмы</a>
        <form action="${pageContext.request.contextPath}/logout" method="post" style="display: inline;">
            <button type="submit" class="logout-btn">Выйти</button>
        </form>
    </div>
</div>

<div class="container">
    <div class="welcome">
        <h2>Добро пожаловать, ${sessionScope.email}!</h2>
        <p>Выберите раздел в меню для продолжения работы.</p>
    </div>
</div>
</body>
</html>