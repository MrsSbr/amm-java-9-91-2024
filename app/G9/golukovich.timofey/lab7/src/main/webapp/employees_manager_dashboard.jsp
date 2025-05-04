<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Employees Dashboard</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            max-width: 1200px;
            margin: 0 auto;
            padding: 20px;
        }
        .menu-item {
            display: inline-block;
            margin-right: 15px;
            padding: 10px 20px;
            background: #2196F3;
            color: white;
            border-radius: 4px;
            text-decoration: none;
        }
        .button-group {
            margin-top: 15px;
            display: flex;
            justify-content: space-between;
        }
        .button-back {
            background: #6c757d;
        }
        .admin-button-group {
            margin-top: 15px;
            display: compact;
            justify-content: space-around;
        }
        .employees-table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
        }
        .employees-table th, .employees-table td {
            border: 1px solid #ddd;
            padding: 12px;
            text-align: left;
        }
        .employees-table tr:nth-child(even) {
            background-color: #f2f2f2;
        }
        .task-button {
            display: inline-block;
            padding: 5px 10px;
            background-color: #2196F3;
            color: white;
            text-decoration: none;
            border-radius: 4px;
            transition: background-color 0.3s;
        }
        .task-button:hover {
            background-color: #2196F3;
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

<h1>Employees</h1>
<c:if test="${not empty requestScope.errorMessage}">
    <div class="error">${requestScope.errorMessage}</div>
    <% request.removeAttribute("errorMessage"); %>
</c:if>
<c:if test="${not empty requestScope.successMessage}">
    <div class="success">${requestScope.successMessage}</div>
    <% request.removeAttribute("successMessage"); %>
</c:if>

<table class="employees-table">
    <thead>
    <tr>
        <th>Id</th>
        <th>Hotel id</th>
        <th>Login</th>
        <th>Name</th>
        <th>Phone number</th>
        <th>Email</th>
        <th>Passport number</th>
        <th>Passport series</th>
        <th>Post</th>
        <th>Salary</th>
        <th>Birthday</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${requestScope.employees_table}" var="staff_employee">
        <tr>
            <td>${staff_employee.id}</td>
            <td>${staff_employee.hotelId}</td>
            <td>${staff_employee.login}</td>
            <td>${staff_employee.name}</td>
            <td>${staff_employee.phoneNumber}</td>
            <td>${staff_employee.email}</td>
            <td>${staff_employee.passportNumber}</td>
            <td>${staff_employee.passportSeries}</td>
            <td>${staff_employee.post}</td>
            <td>${staff_employee.salary}</td>
            <td>${staff_employee.birthday}</td>
            <td>
                <form action="${pageContext.request.contextPath}/api/manage_tasks" method="GET">
                    <input type="hidden" name="current_employee_id" value="${staff_employee.id}">
                    <button type="submit" class="task-button">Tasks</button>
                </form>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>

<div class="button-group">
    <a href="${pageContext.request.contextPath}/api/main" class="menu-item button-back">Back</a>
</div>
</body>
</html>