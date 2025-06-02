<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Hotel Management Dashboard</title>
    <style>
        .menu {
            background: #f5f5f5;
            padding: 20px;
            border-radius: 8px;
            margin-bottom: 30px;
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
        .menu-item:hover {
            background: #1976D2;
        }
        .post-badge {
            float: right;
            padding: 5px 10px;
            background: #607D8B;
            color: white;
            border-radius: 4px;
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
<div class="post-badge">
    Post: ${sessionScope.employee.post}
</div>

<c:if test="${not empty sessionScope.errorMessage}">
    <div class="error">${sessionScope.errorMessage}</div>
    <% session.removeAttribute("errorMessage"); %>
</c:if>
<c:if test="${not empty sessionScope.successMessage}">
    <div class="success">${sessionScope.successMessage}</div>
    <% session.removeAttribute("successMessage"); %>
</c:if>
<c:if test="${not empty requestScope.errorMessage}">
    <div class="error">${requestScope.errorMessage}</div>
    <% request.removeAttribute("errorMessage"); %>
</c:if>
<c:if test="${not empty requestScope.successMessage}">
    <div class="error">${requestScope.successMessage}</div>
    <% request.removeAttribute("successMessage"); %>
</c:if>

<div class="menu">
    <a href="${pageContext.request.contextPath}/api/profile" class="menu-item">My Profile</a>

    <c:if test="${sessionScope.employee.post.name() eq 'STAFF'}">
        <a href="${pageContext.request.contextPath}/api/tasks_dashboard" class="menu-item">My Tasks</a>
    </c:if>

    <c:if test="${sessionScope.employee.post.name() eq 'MANAGER'}">
        <a href="${pageContext.request.contextPath}/api/employees_manager_dashboard" class="menu-item">Work Schedule</a>
    </c:if>

    <c:if test="${sessionScope.employee.post.name() eq 'ADMINISTRATOR'
                  or sessionScope.employee.post.name() eq 'MASTER_ADMINISTRATOR'}">
        <a href="${pageContext.request.contextPath}/api/rooms_management" class="menu-item">Rooms Management</a>
        <a href="${pageContext.request.contextPath}/api/hotels_management" class="menu-item">Hotels Management</a>
    </c:if>

    <c:if test="${sessionScope.employee.post.name() eq 'ADMINISTRATOR'
                  or sessionScope.employee.post.name() eq 'MASTER_ADMINISTRATOR'}">
        <a href="${pageContext.request.contextPath}/api/employees_admin_dashboard" class="menu-item">Employees table</a>
    </c:if>

    <a href="${pageContext.request.contextPath}/auth/logout" class="menu-item">Logout</a>
</div>
<h2>
    <font size="100vh" >
        Тут могла быть ваша реклама
    </font>
</h2>
</body>
</html>
