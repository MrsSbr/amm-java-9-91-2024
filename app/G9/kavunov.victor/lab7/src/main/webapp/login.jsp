<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Вход в систему</title>
    <style>
        * {
            box-sizing: border-box;
            font-family: 'Helvetica', Arial, sans-serif;
        }

        body {
            margin: 0;
            min-height: 100vh;
            display: flex;
            justify-content: center;
            align-items: center;
            background-color: #0f172a;
            background-image:
                    radial-gradient(circle at 20% 30%, rgba(59, 130, 246, 0.15) 0%, transparent 25%),
                    radial-gradient(circle at 80% 70%, rgba(59, 130, 246, 0.15) 0%, transparent 25%),
                    linear-gradient(to bottom right, #0f172a, #1e293b);
            color: #e2e8f0;
            position: relative;
        }

        body::before {
            content: "";
            position: absolute;
            top: 0;
            left: 0;
            right: 0;
            bottom: 0;
            background:
                    linear-gradient(90deg, rgba(59, 130, 246, 0.05) 1px, transparent 1px),
                    linear-gradient(rgba(59, 130, 246, 0.05) 1px, transparent 1px);
            background-size: 40px 40px;
            opacity: 0.5;
            pointer-events: none;
        }

        .auth-container {
            background: rgba(30, 41, 59, 0.9);
            padding: 2.5rem;
            border-radius: 16px;
            box-shadow: 0 8px 32px rgba(30, 58, 138, 0.4);
            width: 100%;
            max-width: 420px;
            position: relative;
            z-index: 1;
            border: 1px solid rgba(59, 130, 246, 0.2);
            backdrop-filter: blur(8px);
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
            margin-bottom: 1.8rem;
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
    <h1>Добро пожаловать</h1>

    <% if (request.getAttribute("errorMessage") != null) { %>
    <div class="error-message">
        <%= request.getAttribute("errorMessage") %>
    </div>
    <% } %>

    <form method="post" action="/login">
        <div class="form-group">
            <label>Электронная почта или номер телефона</label>
            <input type="text" name="login" placeholder="example@mail.com или +71234567890" required>
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