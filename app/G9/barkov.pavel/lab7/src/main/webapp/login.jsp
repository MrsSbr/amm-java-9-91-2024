<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Вход в систему</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 20px;
            background-color: #f5f5f5;
        }
        .container {
            max-width: 400px;
            margin: 50px auto;
            padding: 30px;
            background: white;
            border-radius: 8px;
            box-shadow: 0 0 10px rgba(0,0,0,0.1);
        }
        .form-group {
            margin-bottom: 25px;
        }
        label {
            display: block;
            margin-bottom: 8px;
            font-weight: bold;
            color: #555;
        }
        input {
            width: 100%;
            padding: 12px;
            border: 1px solid #ddd;
            border-radius: 4px;
            box-sizing: border-box;
            font-size: 16px;
        }
        input:focus {
            outline: none;
            border-color: #2196F3;
            box-shadow: 0 0 5px rgba(33,150,243,0.3);
        }
        button {
            background-color: #2196F3;
            color: white;
            padding: 14px 20px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            width: 100%;
            font-size: 16px;
            transition: background 0.3s;
        }
        button:hover {
            background-color: #1976D2;
        }
        .links {
            text-align: center;
            margin-top: 20px;
        }
        .links a {
            color: #666;
            text-decoration: none;
            font-size: 14px;
        }
        .links a:hover {
            color: #2196F3;
        }
        h2 {
            text-align: center;
            color: #333;
            margin-bottom: 30px;
        }
    </style>
</head>
<body>
<div class="container">
    <h2>Вход в аккаунт</h2>
    <form action="login" method="post">
        <div class="form-group">
            <label for="Email">Email:</label>
            <input type="email"
                   id="Email"
                   name="Email"
                   required
                   placeholder="Введите ваш email"
                   autocomplete="username">
        </div>

        <div class="form-group">
            <label for="Password">Пароль:</label>
            <input type="password"
                   id="Password"
                   name="Password"
                   required
                   placeholder="Введите пароль"
                   autocomplete="current-password"
                   minlength="8">
        </div>

        <button type="submit">Войти</button>
    </form>

    <div class="links">
        <a href="register.jsp">Ещё нет аккаунта? Зарегистрироваться</a><br>
        <a href="forgot-password.jsp">Забыли пароль?</a>
    </div>
</div>
</body>
</html>