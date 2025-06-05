<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <title>Регистрация</title>
    <!-- Подключение Google Fonts -->
    <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@400;700&display=swap" rel="stylesheet" />
    <style>
        * {
            box-sizing: border-box;
        }
        body {
            font-family: 'Roboto', sans-serif;
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            margin: 0;
            padding: 0;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            color: #333;
        }
        .register-container {
            background: #fff;
            padding: 40px 50px;
            border-radius: 12px;
            box-shadow: 0 12px 24px rgba(0,0,0,0.2);
            width: 100%;
            max-width: 400px;
            text-align: center;
        }
        h2 {
            margin-bottom: 30px;
            color: #4a148c;
            font-weight: 700;
            font-size: 2rem;
        }
        form {
            display: flex;
            flex-direction: column;
            gap: 20px;
            text-align: left;
        }
        label {
            font-weight: 600;
            margin-bottom: 6px;
            display: block;
            color: #555;
        }
        input[type="text"],
        input[type="password"] {
            padding: 12px 15px;
            border: 2px solid #ddd;
            border-radius: 8px;
            font-size: 1rem;
            transition: border-color 0.3s ease;
            width: 100%;
        }
        input[type="text"]:focus,
        input[type="password"]:focus {
            border-color: #7b1fa2;
            outline: none;
            box-shadow: 0 0 8px rgba(123, 31, 162, 0.4);
        }
        button {
            background-color: #7b1fa2;
            color: white;
            font-weight: 700;
            padding: 14px;
            border: none;
            border-radius: 10px;
            cursor: pointer;
            font-size: 1.1rem;
            transition: background-color 0.3s ease;
            width: 100%;
        }
        button:hover {
            background-color: #4a148c;
        }
        .error {
            color: #d32f2f;
            background-color: #fce4ec;
            padding: 12px;
            border-radius: 8px;
            margin-bottom: 20px;
            font-weight: 600;
            box-shadow: 0 0 8px rgba(211, 47, 47, 0.3);
            text-align: center;
        }
        .login-link {
            margin-top: 25px;
            text-align: center;
        }
        .login-link a {
            color: #7b1fa2;
            text-decoration: none;
            font-weight: 600;
            transition: color 0.3s ease;
        }
        .login-link a:hover {
            color: #4a148c;
            text-decoration: underline;
        }
        @media (max-width: 480px) {
            .register-container {
                padding: 30px 20px;
                width: 90%;
            }
            h2 {
                font-size: 1.5rem;
            }
            button {
                font-size: 1rem;
            }
        }
    </style>
</head>
<body>
<div class="register-container">
    <h2>Регистрация</h2>

    <c:if test="${not empty error}">
        <div class="error">${error}</div>
    </c:if>

    <form action="${pageContext.request.contextPath}/register" method="post" novalidate>
        <label for="name">Имя:</label>
        <input type="text" id="name" name="name" required placeholder="Введите ваше имя" />

        <label for="email">Email:</label>
        <input type="text" id="email" name="email" required placeholder="your.email@example.com" />

        <label for="password">Пароль:</label>
        <input type="password" id="password" name="password" required placeholder="Введите пароль" />

        <label for="phone">Телефон:</label>
        <input type="text" id="phone" name="phone" required placeholder="+7 (___) ___-__-__" />

        <button type="submit">Зарегистрироваться</button>
    </form>

    <div class="login-link">
        Уже есть аккаунт? <a href="${pageContext.request.contextPath}/signin">Войти</a>
    </div>
</div>
</body>
</html>