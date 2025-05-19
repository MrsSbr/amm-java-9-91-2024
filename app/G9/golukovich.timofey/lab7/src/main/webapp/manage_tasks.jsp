<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
    <title>Task Management</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            max-width: 1800px;
            margin: 0 auto;
            padding: 20px;
        }
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
            margin-top: 20px;
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
            background: #2196F3;
            color: white;
            text-decoration: none;
            border-radius: 4px;
        }
        .delete-form {
            display: inline;
        }
        .delete-btn {
            padding: 5px 10px;
            background: #2196F3;
            color: white;
            text-decoration: none;
            border-radius: 4px;
        }
        .save-btn {
            padding: 5px 10px;
            background: #2196F3;
            color: white;
            text-decoration: none;
            border-radius: 4px;
        }
        .form-row {
            margin-bottom: 10px;
            display: flex;
            gap: 10px;
            align-items: center;
        }
        select, input[type="text"] {
            padding: 6px;
            width: 160px;
        }
        .form-actions {
            margin-top: 10px;
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
        .button-back {
            display: inline-block;
            margin-right: 15px;
            padding: 10px 20px;
            color: white;
            border-radius: 4px;
            text-decoration: none;
            background: #6c757d;
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
        .error {
            color: red;
            margin-bottom: 10px;
        }
        .success {
            color: #4CAF50;
            margin-bottom: 10px;
        }
    </style>
    <script>
        function toggleCreate() {
            const form = document.getElementById('create-form');
            form.classList.toggle('active');

            const button = document.getElementById('create-button');
            if (button.textContent === '-') {
                button.textContent = '+';
            } else {
                button.textContent = '-';
            }
        }

        function toggleEdit(taskId) {
            const editBtn = document.getElementById("editBtn");
            if (editBtn.textContent === "⚙️") {
                editBtn.textContent = "✖️";
            } else {
                editBtn.textContent = "⚙️";
            }

            const manager = document.getElementById("manager-select-" + taskId);
            manager.disabled = !manager.disabled;
            const employee = document.getElementById("employee-select-" + taskId);
            employee.disabled = !employee.disabled;
            const room = document.getElementById("room-select-" + taskId);
            room.disabled = !room.disabled;
            const status = document.getElementById("status-select-" + taskId);
            status.disabled = !status.disabled;
            const description = document.getElementById("description-select-" + taskId);
            description.disabled = !description.disabled
            description.readOnly = !description.readOnly;

            const saveBtn = document.getElementById('saveBtn');
            saveBtn.disabled = !saveBtn.disabled;
            saveBtn.hidden = !saveBtn.hidden;
        }
    </script>
</head>
<body>
<c:if test="${not empty requestScope.errorMessage}">
    <div class="error">${requestScope.errorMessage}</div>
    <% request.removeAttribute("errorMessage"); %>
</c:if>
<c:if test="${not empty requestScope.successMessage}">
    <div class="success">${requestScope.successMessage}</div>
    <% request.removeAttribute("successMessage"); %>
</c:if>

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
    </div>

    <div class="tasks-section">
        <h2>Tasks List</h2>
        <button id="create-button" class="add-button" onclick="toggleCreate()">+</button>

        <div id="create-form" class="create-form">
            <form action="${pageContext.request.contextPath}/api/create_task" method="POST">
                <div class="form-row">
                    <label>Manager:</label>
                    <select required>
                        <option value="${sessionScope.employee.id}">
                            ${sessionScope.employee.name} (${sessionScope.employee.login})
                        </option>
                    </select>
                </div>

                <div class="form-row">
                    <label>Employee:</label>
                    <select name="create_task_employeeId" required>
                        <option value="${requestScope.current_employee.id}">
                            ${requestScope.current_employee.name} (${requestScope.current_employee.login})
                        </option>
                    </select>
                </div>

                <div class="form-row">
                    <label>Room:</label>
                    <select name="create_task_hotelRoomId" required>
                        <c:forEach items="${requestScope.hotel_rooms}" var="room">
                            <option value="${room.id}">
                                ${room.roomNumber} (${room.id})
                            </option>
                        </c:forEach>
                    </select>
                </div>

                <div class="form-row">
                    <label>Description:</label>
                    <input type="text" name="create_task_description" value="">
                </div>

                <div class="form-actions">
                    <button type="submit" class="edit-btn">Create</button>
                </div>
            </form>
        </div>

        <table class="tasks-table">
            <thead>
            <tr>
                <th>Id</th>
                <th>Manager</th>
                <th>Employee</th>
                <th>Room Id</th>
                <th>Status</th>
                <th>Description</th>
                <th>Updated at</th>
                <th>Actions</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${requestScope.tasks}" var="task">
                <tr>
                    <td>${task.id}</td>
                    <td>
                        <select id="manager-select-${task.id}" name="managerLogin" required disabled>
                            <c:forEach items="${requestScope.managers}" var="manager">
                                <option value="${manager.login}"
                                    ${manager.login eq task.managerLogin ? 'selected' : ''}>
                                        ${manager.name} (${manager.login})
                                </option>
                            </c:forEach>
                        </select>
                    </td>
                    <td>
                        <select id="employee-select-${task.id}" name="employeeLogin" required disabled>
                            <c:forEach items="${requestScope.employees}" var="staff_employee">
                                <option value="${staff_employee.login}"
                                    ${staff_employee.login eq task.employeeLogin ? 'selected' : ''}>
                                        ${staff_employee.name} (${staff_employee.login})
                                </option>
                            </c:forEach>
                        </select>
                    </td>
                    <td>
                        <select id="room-select-${task.id}" name="hotelRoomId" required disabled>
                            <c:forEach items="${requestScope.hotel_rooms}" var="room">
                                <option value="${room.id}"
                                    ${room.id == task.hotelRoomId ? 'selected' : ''}>
                                        ${room.roomNumber} (${room.id})
                                </option>
                            </c:forEach>
                        </select>
                    </td>
                    <td>
                        <select id="status-select-${task.id}" name="status" required disabled>
                            <c:forEach items="${requestScope.task_statuses}" var="status_name">
                                <option value="${status_name}"
                                    ${not empty param.task_status and param.task_status eq status_name.name() ? 'selected' : ''}>
                                        ${status_name.name()}
                                </option>
                            </c:forEach>
                        </select>
                    </td>
                    <td>
                        <input id="description-select-${task.id}" type="text" name="description"
                               value="${task.description}" disabled readonly>
                    </td>
                    <td>
                        <label>
                            ${task.getFormattedUpdatedAt()}
                        </label>
                    </td>
                    <td>
                        <div class="action-buttons">
                            <button id="editBtn" onclick="toggleEdit(${task.id})" class="edit-btn">⚙️</button>

                            <form class="delete-form" action="${pageContext.request.contextPath}/api/remove_task"
                                  method="POST">
                                <input type="hidden" name="task_id" value="${task.id}">
                                <input type="hidden" name="task_employee_login" value="${task.employeeLogin}">
                                <button type="submit" class="delete-btn" style="cursor:pointer;">
                                    🗑️
                                </button>
                            </form>

                            <button id="saveBtn" class="save-btn" disabled hidden type="submit" style="cursor:pointer;">
                                ✔️
                            </button>
                        </div>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>

        <div class="button-group">
            <a href="${pageContext.request.contextPath}/api/employees_manager_dashboard" class="button-back">Back</a>
        </div>
    </div>
</div>
</body>
</html>
