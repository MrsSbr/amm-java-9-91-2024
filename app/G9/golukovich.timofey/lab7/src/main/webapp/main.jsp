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
    </style>
</head>
<body>
<div class="post-badge">
    Post: ${sessionScope.employee.post}
</div>
<div class="menu">
    <a href="${pageContext.request.contextPath}/profile" class="menu-item">My Profile</a>

    <a href="/staff/tasks" class="menu-item">My Tasks</a>
    <a href="/main/schedule" class="menu-item">Work Schedule</a>
    <a href="/main/requests" class="menu-item">Submit Request</a>

    <c:if test="${sessionScope.employee.post.name() eq 'MANAGER'
                  or sessionScope.employee.post.name() eq 'ADMINISTRATOR'
                  or sessionScope.employee.post.name() eq 'MASTER_ADMINISTRATOR'}">
        <a href="/main/rooms" class="menu-item">Room Management</a>
        <a href="/main/reports" class="menu-item">Financial Reports</a>
        <a href="${pageContext.request.contextPath}/hotel_manager/employees" class="menu-item">Employees table</a>
    </c:if>

    <a href="logout" class="menu-item">Logout</a>
</div>
</body>
</html>
