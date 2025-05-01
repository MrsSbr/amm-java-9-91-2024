<%--
  Created by IntelliJ IDEA.
  User: Пользователь
  Date: 13.04.2025
  Time: 18:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Главная страница</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f5f5dc;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            margin: 0;
        }

        .main-container {
            background-color: #e9ffd9;
            padding: 30px;
            border-radius: 15px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
            width: 350px;
            text-align: center;
        }

        h1 {
            color: #556b2f;
            margin-bottom: 30px;
        }

        .btn-container {
            display: flex;
            flex-direction: column;
            gap: 15px;
        }

        .btn {
            background-color: #8fbc8f;
            color: white;
            border: none;
            padding: 12px 20px;
            text-align: center;
            text-decoration: none;
            display: inline-block;
            font-size: 16px;
            cursor: pointer;
            border-radius: 20px;
            transition: background-color 0.3s;
            width: 100%;
        }

        .btn:hover {
            background-color: #7ca57c;
        }

        .logout-btn {
            margin-top: 30px;
            background-color: #d2b48c;
        }

        .logout-btn:hover {
            background-color: #c0a47b;
        }

        .welcome-message {
            color: #6b8e23;
            margin-bottom: 20px;
            font-weight: bold;
        }
    </style>
</head>
<body>
<div class="main-container">
    <h1>Система управления инцидентами</h1>

    <div class="welcome-message">
        Добро пожаловать, <%= request.getRemoteUser() %>
    </div>

    <div class="btn-container">
        <button onclick="location.href='incidents.jsp'" class="btn">Посмотреть все инциденты</button>
        <button onclick="location.href='add_incident.jsp'" class="btn">Добавить инцидент</button>
    </div>

    <form action="logout" method="post">
        <button type="submit" class="btn logout-btn">Выйти из системы</button>
    </form>
</div>
</body>
</html>
