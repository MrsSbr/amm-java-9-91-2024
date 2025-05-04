<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Task Management</title>
    <style>
        .container {
            display: flex;
            gap: 20px;
            padding: 20px;
        }

        .current-employee-info {
            width: 300px;
            padding: 20px;
            background: #f5f5f5;
            border-radius: 8px;
        }

        .tasks-section {
            flex-grow: 1;
        }

        .tasks-table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 15px;
        }

        .tasks-table th, .tasks-table td {
            border: 1px solid #ddd;
            padding: 12px;
            text-align: left;
        }

        .tasks-table tr:nth-child(even) {
            background-color: #f9f9f9;
        }

        .action-buttons {
            display: flex;
            gap: 8px;
        }

        .edit-btn {
            padding: 5px 10px;
            background: #4CAF50;
            color: white;
            text-decoration: none;
            border-radius: 4px;
        }

        .delete-form {
            display: inline;
        }

        .edit-form {
            display: none;
            background: #f8f9fa;
            padding: 15px;
            border-radius: 5px;
            margin-top: 10px;
        }

        .edit-form.active {
            display: block;
        }

        .form-row {
            margin-bottom: 10px;
            display: flex;
            gap: 10px;
            align-items: center;
        }

        select, input[type="text"] {
            padding: 6px;
            width: 200px;
        }

        .form-actions {
            margin-top: 10px;
        }

        .header-wrapper {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 15px;
        }

        .add-button {
            background: #4CAF50;
            color: white;
            border: none;
            border-radius: 50%;
            width: 40px;
            height: 40px;
            font-size: 24px;
            cursor: pointer;
            display: flex;
            align-items: center;
            justify-content: center;
            text-decoration: none;
        }

        .create-form {
            display: none;
            background: #f8f9fa;
            padding: 15px;
            border-radius: 5px;
            margin-bottom: 20px;
        }

        .create-form.active {
            display: block;
        }
    </style>
    <script>
        function toggleEditForm(taskId) {
            const form = document.getElementById("edit-form-${taskId}",);
            form.classList.toggle('active');
        }

        function cancelEdit(taskId) {
            document.getElementById(`edit-form-${taskId}`).classList.remove('active');
        }

        function toggleCreateForm() {
            const form = document.getElementById('create-form');
            form.classList.toggle('active');

            const button = document.getElementById('create-button');
            if (button.textContent === '-') {
                button.textContent = '+';
            } else {
                button.textContent = '-';
            }
        }
    </script>
</head>
<body>
<div class="container">
    <div class="current-employee-info">
        <h2>Employee Details</h2>
        <p><strong>Id:</strong> ${requestScope.current_employee.id}</p>
        <p><strong>Hotel id:</strong> ${requestScope.current_employee.hotelId}</p>
        <p><strong>Login:</strong> ${requestScope.current_employee.login}</p>
        <p><strong>Name:</strong> ${requestScope.current_employee.name}</p>
        <p><strong>Post:</strong> ${requestScope.current_employee.post}</p>
        <p><strong>Email:</strong> ${requestScope.current_employee.email}</p>
        <p><strong>Phone number:</strong> ${requestScope.current_employee.phoneNumber}</p>
        <p><strong>Passport number:</strong> ${requestScope.current_employee.passportNumber}</p>
        <p><strong>Passport series:</strong> ${requestScope.current_employee.passportSeries}</p>
        <p><strong>Birthday:</strong> ${requestScope.current_employee.birthday}</p>
        <p><strong>Salary:</strong> ${requestScope.current_employee.salary}</p>
    </div>

    <div class="tasks-section">
        <h2>Tasks List</h2>
        <button id="create-button" class="add-button" onclick="toggleCreateForm()">+</button>

        <div id="create-form" class="create-form">
            <form action="${pageContext.request.contextPath}/api/create_task" method="POST">
                <div class="form-row">
                    <label>Manager:</label>
                    <select name="managerLogin" required>
                        <c:forEach items="${requestScope.managers}" var="manager">
                            <option value="${manager.login}"
                                ${manager.id eq sessionScope.employee.id ? 'selected' : ''}>
                                    ${manager.name} (${manager.login})
                            </option>
                        </c:forEach>
                    </select>
                </div>

                <div class="form-row">
                    <label>Employee:</label>
                    <select name="employeeLogin" required>
                        <c:forEach items="${requestScope.staff}" var="staff_employee">
                            <option value="${staff_employee.login}"
                                ${staff_employee.id eq sessionScope.employee.id ? 'selected' : ''}>
                                    ${staff_employee.name} (${staff_employee.login})
                            </option>
                        </c:forEach>
                    </select>
                </div>

                <div class="form-row">
                    <label>Room:</label>
                    <select name="hotelRoomId" required>
                        <c:forEach items="${requestScope.hotelRooms}" var="room">
                            <option value="${room.id}">
                                Room ${room.number} (${room.type})
                            </option>
                        </c:forEach>
                    </select>
                </div>

                <div class="form-row">
                    <label>Description:</label>
                    <input type="text" name="description" value="">
                </div>

                <div class="form-actions">
                    <button type="submit" class="edit-btn">Create</button>
                </div>
            </form>
        </div>

        <table class="tasks-table">
            <thead>
            <tr>
                <th>ID</th>
                <th>Manager</th>
                <th>Employee</th>
                <th>Room ID</th>
                <th>Description</th>
                <th>Actions</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${requestScope.tasks}" var="task">
                <tr>
                    <td>${task.id}</td>
                    <td>${task.managerLogin}</td>
                    <td>${task.employeeLogin}</td>
                    <td>${task.hotelRoomId}</td>
                    <td>${task.description}</td>
                    <td>
                        <div class="action-buttons">
                            <button onclick="toggleEditForm(${task.id})" class="edit-btn">Edit</button>
                            <form class="delete-form" action="${pageContext.request.contextPath}/api/remove_task"
                                  method="POST">
                                <input type="hidden" name="taskId" value="${task.id}">
                                <button type="submit" style="background:none; border:none; cursor:pointer;">
                                    🗑️
                                </button>
                            </form>
                        </div>
                    </td>
                </tr>

                <tr id="edit-form-${task.id}" class="edit-form">
                    <td colspan="6">
                        <form action="${pageContext.request.contextPath}/api/edit_task" method="POST">
                            <input type="hidden" name="taskId" value="${task.id}">

                            <div class="form-row">
                                <label>Manager:</label>
                                <select name="managerLogin" required>
                                    <c:forEach items="${requestScope.managers}" var="manager">
                                        <option value="${manager.login}"
                                            ${manager.login == task.managerLogin ? 'selected' : ''}>
                                                ${manager.name} (${manager.login})
                                        </option>
                                    </c:forEach>
                                </select>
                            </div>

                            <div class="form-row">
                                <label>Employee:</label>
                                <select name="employeeLogin" required>
                                    <c:forEach items="${requestScope.employees}" var="staff_employee">
                                        <option value="${staff_employee.login}"
                                            ${staff_employee.login == task.employeeLogin ? 'selected' : ''}>
                                                ${staff_employee.name} (${staff_employee.login})
                                        </option>
                                    </c:forEach>
                                </select>
                            </div>

                            <div class="form-row">
                                <label>Room:</label>
                                <select name="hotelRoomId" required>
                                    <c:forEach items="${requestScope.hotelRooms}" var="room">
                                        <option value="${room.id}"
                                            ${room.id == task.hotelRoomId ? 'selected' : ''}>
                                            Room ${room.number} (${room.type})
                                        </option>
                                    </c:forEach>
                                </select>
                            </div>

                            <div class="form-row">
                                <label>Description:</label>
                                <input type="text" name="description"
                                       value="${task.description}">
                            </div>

                            <div class="form-actions">
                                <button type="submit" class="edit-btn">Save</button>
                                <button type="button" class="btn-secondary"
                                        onclick="cancelEdit(${task.id})">Cancel
                                </button>
                            </div>
                        </form>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>
</body>
</html>
