<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Register</title>

    <%--<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">--%>

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
        input[type="email"],
        input[type="password"] {
            width: 100%;
            padding: 0.75rem;
            border: 1px solid #ddd;
            border-radius: 4px;
            font-size: 1rem;
        }

        .error-message {
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
    <h1>Register</h1>

    <c:if test="${not empty errorMessage}">
        <div class="error-message">
                ${errorMessage}
        </div>
    </c:if>

    <form action="${pageContext.request.contextPath}/register" method="post">
        <div class="form-group">
            <label for="username">Username</label>
            <input type="text" id="username" name="username" required
                   placeholder="Enter your username" value="${param.username}">
        </div>

        <div class="form-group">
            <label for="email">Email</label>
            <input type="email" id="email" name="email" required
                   placeholder="Enter your email" value="${param.email}">
        </div>

        <div class="form-group">
            <label for="password">Password</label>
            <input type="password" id="password" name="password" required
                   placeholder="Create a password">
        </div>

        <div class="form-group">
            <label for="confirmPassword">Confirm Password</label>
            <input type="password" id="confirmPassword" name="confirmPassword" required
                   placeholder="Repeat your password">
        </div>

        <button type="submit" class="btn">Register</button>
    </form>

    <p>Already have an account? <a href="${pageContext.request.contextPath}/login">Sign in</a></p>
</div>
</body>
</html>