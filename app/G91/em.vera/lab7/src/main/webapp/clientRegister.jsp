<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Создать аккаунт</title>
    <link href="https://fonts.googleapis.com/css2?family=Poppins&display=swap" rel="stylesheet">
    <style>
        :root {
            --gradient-start: #8e2de2;
            --gradient-end:   #4a00e0;
            --bg-card:        #ffffff;
            --text-dark:      #333333;
            --text-light:     #f5f5f5;
            --input-bg:       #f2f2f7;
            --border-color:   #ddd;
        }
        * { box-sizing: border-box; margin: 0; padding: 0; }
        body {
            background: #f2f2f7;
            font-family: "Poppins", sans-serif;
            color: var(--text-dark);
            display: flex;
            align-items: center;
            justify-content: center;
            min-height: 100vh;
        }
        .container {
            width: 400px;
            background: var(--bg-card);
            border-radius: 1rem;
            box-shadow: 0 8px 20px rgba(0,0,0,0.1);
            overflow: hidden;
        }
        .header {
            padding: 2rem 1.5rem;
            background: linear-gradient(135deg, var(--gradient-start), var(--gradient-end));
            text-align: center;
        }
        .header h2 {
            color: var(--text-light);
            font-size: 1.8rem;
            margin-bottom: 0.5rem;
        }
        .header p {
            color: rgba(255,255,255,0.85);
            font-size: 0.95rem;
        }
        .form-content {
            padding: 2rem 1.5rem;
        }
        .form-group {
            margin-bottom: 1.25rem;
        }
        .form-group input {
            width: 100%;
            padding: 0.75rem 1rem;
            border: 1px solid var(--border-color);
            border-radius: 0.5rem;
            background: var(--input-bg);
            font-size: 0.95rem;
            color: var(--text-dark);
        }
        .form-group input:focus {
            outline: none;
            border-color: var(--gradient-start);
        }
        button {
            width: 100%;
            padding: 0.75rem;
            border: none;
            border-radius: 0.5rem;
            background: linear-gradient(135deg, var(--gradient-start), var(--gradient-end));
            color: white;
            font-size: 1rem;
            font-weight: bold;
            cursor: pointer;
            transition: opacity 0.3s;
        }
        button:hover {
            opacity: 0.9;
        }
        .footer {
            text-align: center;
            padding: 1rem 0;
            font-size: 0.9rem;
        }
        .footer a {
            color: var(--gradient-end);
            text-decoration: none;
            font-weight: bold;
        }
        .footer a:hover {
            text-decoration: underline;
        }
    </style>
</head>
<body>
<div class="container">
    <div class="header">
        <h2>Создать аккаунт</h2>
        <p>Запишитесь на сессию уже сегодня</p>
    </div>

    <form action="${pageContext.request.contextPath}/client/register" method="post" class="form-content">
        <div class="form-group">
            <input type="text" name="name"
                   placeholder="Имя"
                   autocomplete="off"
                   required />
        </div>
        <div class="form-group">
            <input type="email" name="email"
                   placeholder="Email"
                   autocomplete="off"
                   required />
        </div>
        <div class="form-group">
            <input type="password" name="password"
                   placeholder="Пароль"
                   autocomplete="new-password"
                   required />
        </div>
        <button type="submit">Зарегистрироваться</button>
    </form>

    <div class="footer">
        Уже есть аккаунт? <a href="${pageContext.request.contextPath}/client/login">Войти</a>
    </div>
    <div class="footer">
        <a href="${pageContext.request.contextPath}/psychologist/login">Войти как психолог</a>
    </div>
</div>
</body>
</html>
