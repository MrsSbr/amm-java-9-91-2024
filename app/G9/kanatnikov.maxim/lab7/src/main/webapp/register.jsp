<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Регистрация</title>
    <style>
        /* Все стили из login.jsp */
        * {
            box-sizing: border-box;
            font-family: 'Arial', sans-serif;
        }

        body {
            margin: 0;
            min-height: 100vh;
            display: flex;
            justify-content: center;
            align-items: center;
            background: #f0f2f5;
        }

        .auth-container {
            background: white;
            padding: 2rem;
            border-radius: 8px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.1);
            width: 100%;
            max-width: 500px;
        }

        h1 {
            text-align: center;
            color: #1a1a1a;
            margin-bottom: 2rem;
        }

        .form-group {
            margin-bottom: 1.5rem;
        }

        label {
            display: block;
            margin-bottom: 0.5rem;
            color: #555;
        }

        input {
            width: 100%;
            padding: 0.8rem;
            border: 1px solid #ddd;
            border-radius: 4px;
            font-size: 1rem;
        }

        button {
            width: 100%;
            padding: 1rem;
            background: #4CAF50;
            color: white;
            border: none;
            border-radius: 4px;
            font-size: 1rem;
            cursor: pointer;
            transition: background 0.3s;
        }

        button:hover {
            background: #45a049;
        }

        .link-section {
            text-align: center;
            margin-top: 1.5rem;
        }

        a {
            color: #4CAF50;
            text-decoration: none;
        }

        .error-message {
            color: #dc3545;
            padding: 0.5rem;
            margin-bottom: 1rem;
            text-align: center;
            border-radius: 4px;
        }
    </style>
</head>
<body>
<div class="auth-container">
    <h1>Регистрация</h1>

    <% if (request.getAttribute("errorMessage") != null) { %>
    <div class="error-message">
        <%= request.getAttribute("errorMessage") %>
    </div>
    <% } %>

    <form method="post" action="/register">
        <div class="form-group">
            <label>Фамилия</label>
            <input type="text" name="lastName" required>
        </div>

        <div class="form-group">
            <label>Имя</label>
            <input type="text" name="firstName" required>
        </div>

        <div class="form-group">
            <label>Отчество</label>
            <input type="text" name="patronymic">
        </div>

        <div class="form-group">
            <label>Город</label>
            <input type="text" name="city" required>
        </div>

        <div class="form-group">
            <label>Email</label>
            <input type="email" name="email" required>
        </div>

        <div class="form-group">
            <label>Телефон</label>
            <input type="tel" name="phoneNumber" required>
        </div>

        <div class="form-group">
            <label>Пароль</label>
            <input type="password" name="password" required>
        </div>

        <button type="submit">Зарегистрироваться</button>
    </form>

    <div class="link-section">
        <p>Уже есть аккаунт? <a href="/login.jsp">Войти</a></p>
    </div>
</div>
</body>
</html>