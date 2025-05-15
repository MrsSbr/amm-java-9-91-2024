<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Вход в систему</title>
    <style>
        * {
            box-sizing: border-box;
            font-family: 'Segoe UI', system-ui, sans-serif;
        }

        body {
            margin: 0;
            min-height: 100vh;
            display: flex;
            justify-content: center;
            align-items: center;
            background: linear-gradient(135deg, #f3e8ff 0%, #f8fafc 100%);
        }

        .auth-container {
            background: white;
            padding: 2.5rem;
            border-radius: 16px;
            box-shadow: 0 8px 24px rgba(106, 33, 182, 0.15);
            width: 100%;
            max-width: 420px;
            transform: translateY(-10px);
        }

        h1 {
            text-align: center;
            color: #4c1d95;
            margin-bottom: 2rem;
            font-weight: 600;
            font-size: 1.8rem;
            letter-spacing: -0.5px;
        }

        .form-group {
            margin-bottom: 1.8rem;
        }

        label {
            display: block;
            margin-bottom: 0.6rem;
            color: #475569;
            font-weight: 500;
            font-size: 0.9rem;
        }

        input {
            width: 100%;
            padding: 1rem;
            border: 2px solid #e2e8f0;
            border-radius: 8px;
            font-size: 1rem;
            transition: all 0.3s ease;
        }

        input:focus {
            outline: none;
            border-color: #8b5cf6;
            box-shadow: 0 0 0 3px rgba(139, 92, 246, 0.2);
        }

        button {
            width: 100%;
            padding: 1rem;
            background: #7c3aed;
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
            background: #6d28d9;
            transform: translateY(-1px);
        }

        .link-section {
            text-align: center;
            margin-top: 2rem;
            color: #64748b;
            font-size: 0.95rem;
        }

        a {
            color: #7c3aed;
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
            background: #7c3aed;
            transition: width 0.3s ease;
        }

        a:hover::after {
            width: 100%;
        }

        .error-message {
            color: #dc2626;
            background: #fee2e2;
            padding: 1rem;
            margin-bottom: 1.5rem;
            text-align: center;
            border-radius: 8px;
            border: 1px solid #fca5a5;
            font-size: 0.9rem;
        }
    </style>
</head>
<body>
<div class="auth-container">
    <h1>Добро пожаловать</h1>

    <% if (request.getAttribute("errorMessage") != null) { %>
    <div class="error-message">
        <%= request.getAttribute("errorMessage") %>
    </div>
    <% } %>

    <form method="post" action="/login">
        <div class="form-group">
            <label>Электронная почта</label>
            <input type="email" name="email" placeholder="example@mail.com" required>
        </div>

        <div class="form-group">
            <label>Пароль</label>
            <input type="password" name="password" placeholder="••••••••" required>
        </div>

        <button type="submit">Продолжить</button>
    </form>

    <div class="link-section">
        <p>Нет учётной записи? <a href="/register.jsp">Создать аккаунт</a></p>
    </div>
</div>
</body>
</html>