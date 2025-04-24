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
    </style>
</head>
<body>

<h1>Employees</h1>
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
    <c:forEach items="${requestScope.employees_table}" var="employee">
        <tr>
            <td>${employee.id}</td>
            <td>${employee.hotelId}</td>
            <td>${employee.login}</td>
            <td>${employee.name}</td>
            <td>${employee.phoneNumber}</td>
            <td>${employee.email}</td>
            <td>${employee.passportNumber}</td>
            <td>${employee.passportSeries}</td>
            <td>${employee.post}</td>
            <td>${employee.salary}</td>
            <td>${employee.birthday}</td>
        </tr>
    </c:forEach>
    </tbody>
</table>

<div class="button-group">
    <a href="${pageContext.request.contextPath}/main" class="menu-item button-back">Back</a>
    <c:if test="${sessionScope.employee.post eq 'MANAGER'}">
        <a href="${pageContext.request.contextPath}/hotel_manager/employees/tasks_Scheduler" class="menu-item">Schedule tasks</a>
    </c:if>
    <c:if test="${sessionScope.employee.post eq 'ADMINISTRATOR'
                  or sessionScope.employee.post.name() eq 'MASTER_ADMINISTRATOR'}">
        <span class="admin-button-group">
            <a href="${pageContext.request.contextPath}/hotel_admin/register" class="menu-item">Register employee</a>
            <a href="${pageContext.request.contextPath}/hotel_admin/unregister" class="menu-item">Unregister employee</a>
        </span>
    </c:if>
</div>
</body>
</html>