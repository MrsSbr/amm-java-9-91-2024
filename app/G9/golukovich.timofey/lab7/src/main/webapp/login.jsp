<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Hotel Staff - Register employee</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            max-width: 300px;
            margin: 50px auto;
        }
        .form-group {
            margin-bottom: 15px;
        }
        input {
            width: 100%;
            padding: 8px;
        }
        button {
            background: #4CAF50;
            color: white;
            padding: 10px 20px;
            border: none;
            cursor: pointer;
        }
        .error {
            color: red;
            margin-bottom: 10px;
        }
    </style>
</head>
<body>
<h2>Staff Login</h2>

<c:if test="${not empty requestScope.errorMessage}">
    <div class="error">${requestScope.errorMessage}</div>
    <% request.removeAttribute("errorMessage"); %>
</c:if>

<form action="login" method="POST">
    <div class="form-group">
        <input type="text" name="login" placeholder="Login" required>
    </div>
    <div class="form-group">
        <input type="password" name="password" placeholder="Password" required>
    </div>
    <button type="submit">Login</button>
</form>
</body>
</html>
