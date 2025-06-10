<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Личный кабинет</title>
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

        .profile-container {
            background-color: #e9ffd9;
            padding: 30px;
            border-radius: 15px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
            width: 350px;
            text-align: center;
        }

        h1 {
            color: #556b2f;
            margin-bottom: 20px;
        }

        .user-info {
            text-align: left;
            margin-bottom: 20px;
        }

        .user-info p {
            margin: 10px 0;
            color: #6b8e23;
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
    </style>
</head>
<body>
<div class="profile-container">
    <h1>Личный кабинет</h1>

    <div class="user-info">
        <p><strong>Логин:</strong> ${employee.login}</p>
        <p><strong>Фамилия:</strong> ${employee.surnameEmpl}</p>
        <p><strong>Имя:</strong> ${employee.nameEmpl}</p>
        <p><strong>Отчество:</strong> ${employee.patronumicEmpl}</p>
        <p><strong>Дата рождения:</strong> ${employee.dateOfBirthEmpl}</p>
    </div>

    <button onclick="location.href='mainPage.jsp'" class="btn">На главную</button>
</div>
</body>
</html>