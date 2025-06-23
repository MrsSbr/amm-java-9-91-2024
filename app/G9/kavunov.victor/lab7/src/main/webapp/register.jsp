<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Регистрация</title>
    <style>
        * {
            box-sizing: border-box;
            font-family: 'Helvetica', Arial, sans-serif;
        }

        body {
            margin: 0;
            min-height: 100vh;
            display: block;
            background-color: #0f172a;
            background-image:
                    radial-gradient(circle at 20% 30%, rgba(59, 130, 246, 0.15) 0%, transparent 25%),
                    radial-gradient(circle at 80% 70%, rgba(59, 130, 246, 0.15) 0%, transparent 25%),
                    linear-gradient(to bottom right, #0f172a, #1e293b);
            color: #e2e8f0;
            padding: 20px;
        }

        .container-wrapper {
            display: flex;
            justify-content: center;
            min-height: 100vh;
            align-items: center;
            padding: 40px 0;
        }

        .auth-container {
            background: rgba(30, 41, 59, 0.9);
            padding: 2.5rem;
            border-radius: 16px;
            box-shadow: 0 8px 32px rgba(30, 58, 138, 0.4);
            width: 100%;
            max-width: 500px;
            border: 1px solid rgba(59, 130, 246, 0.2);
            backdrop-filter: blur(8px);
            margin: 40px auto;login.jsp
        }
        h1 {
            text-align: center;
            color: #60a5fa;
            margin-bottom: 2rem;
            font-weight: 600;
            font-size: 1.8rem;
            letter-spacing: -0.5px;
        }

        .form-group {
            margin-bottom: 1.5rem;
        }

        label {
            display: block;
            margin-bottom: 0.6rem;
            color: #94a3b8;
            font-weight: 500;
            font-size: 0.9rem;
        }

        input {
            width: 100%;
            padding: 1rem;
            background: rgba(51, 65, 85, 0.7);
            border: 2px solid #475569;
            border-radius: 8px;
            font-size: 1rem;
            transition: all 0.3s ease;
            color: #f8fafc;
        }

        input::placeholder {
            color: #64748b;
        }

        input:focus {
            outline: none;
            border-color: #3b82f6;
            box-shadow: 0 0 0 3px rgba(59, 130, 246, 0.3);
        }

        button {
            width: 100%;
            padding: 1rem;
            background: #2563eb;
            color: white;
            border: none;
            border-radius: 8px;
            font-size: 1rem;
            font-weight: 600;
            cursor: pointer;
            transition: all 0.3s ease;
            text-transform: uppercase;
            letter-spacing: 0.5px;
        }

        button:hover {
            background: #1d4ed8;
            transform: translateY(-1px);
        }

        .link-section {
            text-align: center;
            margin-top: 2rem;
            color: #94a3b8;
            font-size: 0.95rem;
        }

        a {
            color: #60a5fa;
            text-decoration: none;
            font-weight: 600;
            position: relative;
        }

        a::after {
            content: '';
            position: absolute;
            bottom: -2px;
            left: 0;
            width: 0;
            height: 2px;
            background: #60a5fa;
            transition: width 0.3s ease;
        }

        a:hover::after {
            width: 100%;
        }

        .error-message {
            color: #fecaca;
            background: rgba(127, 29, 29, 0.7);
            padding: 1rem;
            margin-bottom: 1.5rem;
            text-align: center;
            border-radius: 8px;
            border: 1px solid #ef4444;
            font-size: 0.9rem;
        }
    </style>
</head>
<body>
<div class="auth-container">
    <h1>Создать аккаунт</h1>

    <% if (request.getAttribute("errorMessage") != null) { %>
    <div class="error-message">
        <%= request.getAttribute("errorMessage") %>
    </div>
    <% } %>

    <form method="post" action="/register">
        <div class="form-group">
            <label>Фамилия</label>
            <input type="text" name="surname" placeholder="Иванов" required>
        </div>

        <div class="form-group">
            <label>Имя</label>
            <input type="text" name="name" placeholder="Иван" required>
        </div>

        <div class="form-group">
            <label>Отчество</label>
            <input type="text" name="patronymicname" placeholder="Иванович">
        </div>

        <div class="form-group">
            <label>Email</label>
            <input type="email" name="email" placeholder="example@mail.com" required>
        </div>

        <div class="form-group">
            <label>Телефон</label>
            <input type="tel" name="phoneNumber" placeholder="+79999999999" required
                   pattern="\+7\d{10}" title="Формат: +7XXXXXXXXXX">
        </div>

        <div class="form-group">
            <label>Пароль</label>
            <input type="password" name="password" placeholder="••••••••" required>
        </div>

        <button type="submit">Создать аккаунт</button>
    </form>

    <div class="link-section">
        <p>Уже есть аккаунт? <a href="/login.jsp">Войти</a></p>
    </div>
</div>
</body>
</html>