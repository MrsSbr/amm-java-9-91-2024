<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Profile</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            max-width: 600px;
            margin: 20px auto;
            padding: 20px;
        }
        .profile-section {
            border: 1px solid #ddd;
            padding: 20px;
            border-radius: 5px;
        }
        .form-group {
            margin-bottom: 15px;
        }
        .form-group label {
            display: block;
            margin-bottom: 5px;
            font-weight: bold;
        }
        .form-control {
            width: 100%;
            padding: 8px;
            border: 1px solid #ddd;
            border-radius: 4px;
            box-sizing: border-box;
        }
        .form-control:read-only {
            background-color: #f5f5f5;
        }
        .editable {
        }
        .button-group {
            margin-top: 20px;
            text-align: right;
        }
        button {
            padding: 8px 16px;
            margin-left: 10px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
        }
        .button-action {
            background-color: #4CAF50;
            color: white;
            display: compact;
        }
        .button-back {
            background-color: #6c757d;
            color: white;
        }
        button:disabled {
            background-color: #cccccc;
            cursor: not-allowed;
        }
    </style>
    <script>
        function toggleEdit() {
            const editBtn = document.getElementById("editBtn");
            if (editBtn.textContent === "Edit") {
                editBtn.textContent = "Close";
            } else {
                editBtn.textContent = "Edit";
            }

            const inputs = document.querySelectorAll('.editable');
            inputs.forEach(input => {
                input.readOnly = !input.readOnly;
            });

            const saveBtn = document.getElementById('saveBtn');
            saveBtn.disabled = !saveBtn.disabled;
        }
    </script>
</head>
<body>
<div class="profile-section">
    <h2>My Profile</h2>
    <form id="profileForm" action="${pageContext.request.contextPath}/api/profile" method="POST">
        <div class="form-group">
            <label>Id:</label>
            <input type="text" class="form-control" name="id" value="${sessionScope.employee.id}" readonly>
        </div>
        <div class="form-group">
            <label>Hotel name:</label>
            <input type="text" class="form-control" name="hotel_name" value="${hotel_name}" readonly>
        </div>
        <div class="form-group">
            <label>Login:</label>
            <input type="text" class="form-control" name="login" value="${sessionScope.employee.login}" readonly>
        </div>
        <div class="form-group">
            <label>Post:</label>
            <input type="text" class="form-control" name="post" value="${sessionScope.employee.post}" readonly>
        </div>
        <div class="form-group editable">
            <label>Name:</label>
            <input type="text" class="form-control editable" name="name" value="${sessionScope.employee.name}" readonly>
        </div>
        <div class="form-group">
            <label>Phone number:</label>
            <input type="text" class="form-control editable" name="phone_number" value="${sessionScope.employee.phoneNumber}" readonly>
        </div>
        <div class="form-group editable">
            <label>Email:</label>
            <input type="email" class="form-control editable" name="email" value="${sessionScope.employee.email}" readonly>
        </div>
        <div class="form-group">
            <label>Passport number:</label>
            <input type="text" class="form-control editable" name="passport_number" value="${sessionScope.employee.passportNumber}" readonly>
        </div>
        <div class="form-group">
            <label>Passport series:</label>
            <input type="text" class="form-control editable" name="passport_series" value="${sessionScope.employee.passportSeries}" readonly>
        </div>
        <div class="form-group editable">
            <label>Birthday:</label>
            <input type="text" class="form-control editable" name="birthday" value="${sessionScope.employee.getFormattedBirthday()}" readonly>
        </div>

        <div class="button-group">
            <button type="button" class="button-back" onclick="window.location.href='${pageContext.request.contextPath}/api/main'">Back</button>
            <button type="button" class="button-action" id="editBtn" onclick="toggleEdit()">Edit</button>
            <button type="submit" class="button-action" id="saveBtn" disabled>Save</button>
        </div>
    </form>
</div>
</body>
</html>
