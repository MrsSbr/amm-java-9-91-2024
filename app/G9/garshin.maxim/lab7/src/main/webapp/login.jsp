<%--
  Created by IntelliJ IDEA.
  User: Максим Гаршин
  Date: 31.03.2025
  Time: 15:07
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Login</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <%--<link rel="stylesheet" href="${pageContext.request.contextPath}/css/login.css">--%>
    <style>
        * {
            box-sizing: border-box;
            margin: 0;
            padding: 0;
            font-family: Arial, sans-serif;
        }

        body {
            background-color: #f5f5f5;
            display: flex;
            justify-content: center;
            align-items: center;
            min-height: 100vh;
            padding: 20px;
        }

        .container {
            background: white;
            padding: 2rem;
            border-radius: 8px;
            width: 100%;
            max-width: 400px;
            text-align: center;
            box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
        }

        h1 {
            margin-bottom: 1.5rem;
            color: #333;
        }

        .form-group {
            margin-bottom: 1rem;
            text-align: left;
        }

        label {
            display: block;
            margin-bottom: 0.5rem;
            color: #555;
        }

        input[type="text"],
        input[type="password"] {
            width: 100%;
            padding: 0.75rem;
            border: 1px solid #ddd;
            border-radius: 4px;
            font-size: 1rem;
        }

        .error {
            color: #d32f2f;
            background-color: #fce4e4;
            padding: 0.75rem;
            border-radius: 4px;
            margin-bottom: 1rem;
        }

        .btn {
            display: inline-block;
            background-color: #42a5f5;
            color: white;
            border: none;
            padding: 0.75rem 1.5rem;
            border-radius: 4px;
            cursor: pointer;
            margin-top: 1rem;
            font-size: 1rem;
            transition: all 0.3s ease;
            width: 100%;
        }

        .btn:hover {
            background-color: #1e88e5;
            transform: scale(1.02);
        }

        p {
            margin-top: 1.5rem;
            color: #666;
        }

        a {
            color: #42a5f5;
            text-decoration: none;
        }

        a:hover {
            text-decoration: underline;
        }
    </style>
</head>
<body>
<div class="container">
    <h1>Login</h1>
    <c:if test="${not empty errorMessage}">
        <div class="error">${errorMessage}</div>
    </c:if>
    <form action="${pageContext.request.contextPath}/login" method="post">
        <div class="form-group">
            <label for="username">Username:</label>
            <input type="text" id="username" name="username" required>
        </div>
        <div class="form-group">
            <label for="password">Password:</label>
            <input type="password" id="password" name="password" required>
        </div>
        <button type="submit" class="btn">Login</button>
    </form>
    <p>Don't have an account? <a href="${pageContext.request.contextPath}/register">Register</a></p>
</div>
</body>
</html>