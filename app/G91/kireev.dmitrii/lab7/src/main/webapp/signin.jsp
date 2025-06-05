<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <title>Sign In</title>
    <!-- Подключение Google Fonts для современного шрифта -->
    <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@400;700&display=swap" rel="stylesheet" />
    <style>
        /* Сброс отступов и базовые стили */
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
        .container {
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
        input[type="submit"] {
            background-color: #7b1fa2;
            color: white;
            font-weight: 700;
            padding: 14px;
            border: none;
            border-radius: 10px;
            cursor: pointer;
            font-size: 1.1rem;
            transition: background-color 0.3s ease;
        }
        input[type="submit"]:hover {
            background-color: #4a148c;
        }
        .error-message {
            color: #d32f2f;
            background-color: #fce4ec;
            padding: 12px;
            border-radius: 8px;
            margin-bottom: 20px;
            font-weight: 600;
            box-shadow: 0 0 8px rgba(211, 47, 47, 0.3);
        }
        .register-link {
            margin-top: 25px;
            display: inline-block;
            color: #7b1fa2;
            text-decoration: none;
            font-weight: 600;
            transition: color 0.3s ease;
        }
        .register-link:hover {
            color: #4a148c;
            text-decoration: underline;
        }
        @media (max-width: 480px) {
            .container {
                padding: 30px 20px;
                width: 90%;
            }
            h2 {
                font-size: 1.5rem;
            }
            input[type="submit"] {
                font-size: 1rem;
            }
        }
    </style>
</head>
<body>
<div class="container">
    <h2>Sign In</h2>

    <% String errorMessage = (String) request.getAttribute("errorMessage"); %>
    <% if (errorMessage != null) { %>
    <p class="error-message"><%= errorMessage %></p>
    <% } %>

    <form action="signin" method="post" novalidate>
        <label for="email">Email:</label>
        <input type="text" id="email" name="email" required placeholder="your.email@example.com" />

        <label for="password">Password:</label>
        <input type="password" id="password" name="password" required placeholder="Enter your password" />

        <input type="submit" value="Sign In" />
    </form>

    <a class="register-link" href="register">Don't have an account? Register</a>
</div>
</body>
</html>