<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Hotel administrator: unregister</title>
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
        .button-back {
            background: #6c757d;
            color: white;
            padding: 10px 20px;
            border: none;
            cursor: pointer;
        }
        .button-group {
            margin-top: 15px;
            display: flex;
            justify-content: space-between;
        }

        select {
            width: 100%;
            padding: 8px;
            margin-bottom: 15px;
        }

        .error {
            color: red;
            margin-bottom: 10px;
        }

        .success {
            color: #4CAF50;
            margin-bottom: 10px;
        }
    </style>
</head>
<body>
<h2>Unregister employee</h2>

<c:if test="${not empty errorMessage}">
    <div class="error">${errorMessage}</div>
</c:if>

<c:if test="${not empty successMessage}">
    <div class="success">${successMessage}</div>
</c:if>

<form action="${pageContext.request.contextPath}/api/unregister" method="POST">
    <div class="form-group">
        <input type="text" name="login" placeholder="Login" required>
    </div>
    <div class="button-group">
        <button type="button" class="button-back" onclick="window.location.href='${pageContext.request.contextPath}/api/employees_admin_dashboard'">
            Back
        </button>
        <button type="submit">Unregister</button>
    </div>
</form>
</body>
</html>
