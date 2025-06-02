<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Hotel administrator: register</title>
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
<h2>Register employee</h2>

<c:if test="${not empty requestScope.errorMessage}">
    <div class="error">${requestScope.errorMessage}</div>
    <% request.removeAttribute("errorMessage"); %>
</c:if>

<c:if test="${not empty requestScope.successMessage}">
    <div class="success">${requestScope.successMessage}</div>
    <% request.removeAttribute("successMessage"); %>
</c:if>

<form action="${pageContext.request.contextPath}/api/register" method="POST">
    <div class="form-group">
        <input type="text" name="login" placeholder="Login" required>
    </div>
    <div class="form-group">
        <input type="password" name="password" placeholder="Password" required>
    </div>
    <div class="form-group">
        <label>Select post:</label>
        <select name="post" required>
            <c:forEach items="${requestScope.employee_posts}" var="post_name">
                <option value="${post_name}"
                    ${not empty param.post and param.post eq post_name.name() ? 'selected' : ''}>
                        ${post_name.name()}
                </option>
            </c:forEach>
        </select>
    </div>
    <div class="form-group">
        <label>Select hotel:</label>
        <select name="hotel_id" required>
            <c:forEach items="${requestScope.hotels}" var="hotel">
                <option value="${hotel.id}">${hotel.name} (ID: ${hotel.id})</option>
            </c:forEach>
        </select>
    </div>
    <div class="button-group">
        <button type="button" class="button-back" onclick="window.location.href='${pageContext.request.contextPath}/api/employees_admin_dashboard'">
            Back
        </button>
        <button type="submit">Register</button>
    </div>
</form>
</body>
</html>
