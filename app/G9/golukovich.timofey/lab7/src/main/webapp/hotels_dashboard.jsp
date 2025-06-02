<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <title>Hotels Management</title>
    <style>
        .container {
            max-width: 1200px;
            margin: 20px auto;
            padding: 20px;
        }
        .hotel-table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
        }
        .hotel-table th, .hotel-table td {
            border: 1px solid #ddd;
            padding: 12px;
            text-align: left;
        }
        .hotel-table tr:nth-child(even) {
            background-color: #f9f9f9;
        }
        .action-buttons {
            display: flex;
            gap: 5px;
        }
        .btn {
            padding: 6px 12px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
        }
        .btn-primary {
            background-color: #4CAF50;
            color: white;
        }
        .btn-danger {
            background-color: #f44336;
            color: white;
        }
        .btn-edit {
            background-color: #2196F3;
            color: white;
        }
        .form-section {
            margin-top: 30px;
            padding: 20px;
            border: 1px solid #ddd;
        }
        .form-group {
            margin-bottom: 15px;
        }
        .form-group label {
            display: block;
            margin-bottom: 5px;
        }
        .form-control {
            width: 100%;
            padding: 8px;
            border: 1px solid #ddd;
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
<c:if test="${not empty requestScope.errorMessage}">
    <div class="error">${requestScope.errorMessage}</div>
    <% request.removeAttribute("errorMessage"); %>
</c:if>
<c:if test="${not empty requestScope.successMessage}">
    <div class="success">${requestScope.successMessage}</div>
    <% request.removeAttribute("successMessage"); %>
</c:if>

<div class="container">
    <h1>Hotels Management</h1>

    <table class="hotel-table">
        <thead>
        <tr>
            <th>ID</th>
            <th>Name</th>
            <th>Address</th>
            <th>Email</th>
            <th>Phone</th>
            <th>Opening Date</th>
            <th>Actions</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${requestScope.hotels_table}" var="hotel">
            <tr>
                <td>${hotel.id}</td>
                <td>${hotel.name}</td>
                <td>${hotel.address}</td>
                <td>${hotel.email}</td>
                <td>${hotel.phoneNumber}</td>
                <td>${hotel.getFormattedOpeningDate()}</td>
                <td>
                    <div class="action-buttons">
                        <button class="btn btn-edit"
                                onclick="openEditModal(${hotel.id})">Edit
                        </button>
                        <form action="${pageContext.request.contextPath}/api/delete_hotel" method="POST" style="margin:0;">
                            <input type="hidden" name="hotelId" value="${hotel.id}">
                            <button type="submit" class="btn btn-danger">Delete</button>
                        </form>
                    </div>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

    <div class="form-section">
        <h2>Create New Hotel</h2>
        <form action="${pageContext.request.contextPath}/api/create_hotel" method="POST">
            <div class="form-group">
                <label>Name:</label>
                <input type="text" class="form-control" name="name" required>
            </div>
            <div class="form-group">
                <label>Address:</label>
                <input type="text" class="form-control" name="address" required>
            </div>
            <div class="form-group">
                <label>Email:</label>
                <input type="email" class="form-control" name="email" required>
            </div>
            <div class="form-group">
                <label>Phone Number:</label>
                <input type="tel" class="form-control" name="phoneNumber" required>
            </div>
            <div class="form-group">
                <label>Opening Date:</label>
                <input type="date" class="form-control" name="openingDate" required>
            </div>
            <button type="submit" class="btn btn-primary">Create Hotel</button>
        </form>
    </div>

    <div id="editModal" style="display:none; position:fixed; top:20%; left:30%;
             background:white; padding:20px; border:1px solid #ddd; z-index:1000;">
        <h3>Edit Hotel</h3>
        <form id="editForm" action="${pageContext.request.contextPath}/api/edit_hotel" method="POST">
            <input type="hidden" id="editId" name="id">
            <div class="form-group">
                <label>Name:</label>
                <input type="text" class="form-control" id="editName" name="name" required>
            </div>
            <div class="form-group">
                <label>Address:</label>
                <input type="text" class="form-control" id="editAddress" name="address" required>
            </div>
            <div class="form-group">
                <label>Email:</label>
                <input type="email" class="form-control" id="editEmail" name="email" required>
            </div>
            <div class="form-group">
                <label>Phone:</label>
                <input type="tel" class="form-control" id="editPhone" name="phoneNumber" required>
            </div>
            <div class="form-group">
                <label>Opening Date:</label>
                <input type="date" class="form-control" id="editDate" name="openingDate" required>
            </div>
            <div class="form-group">
                <button type="submit" class="btn btn-primary">Save Changes</button>
                <button type="button" class="btn btn-secondary"
                        onclick="document.getElementById('editModal').style.display='none'">
                    Cancel
                </button>
            </div>
        </form>
    </div>

    <a href="${pageContext.request.contextPath}/api/main" class="btn btn-primary" style="margin-top:20px;">Back</a>
</div>

<script>
    function openEditModal(hotelId) {
        fetch('get-hotel?id=' + hotelId)
            .then(response => response.json())
            .then(hotel => {
                document.getElementById('editName').value = hotel.name;
                document.getElementById('editAddress').value = hotel.address;
                document.getElementById('editEmail').value = hotel.email;
                document.getElementById('editPhone').value = hotel.phoneNumber;
                document.getElementById('editDate').value = hotel.openingDate;
                document.getElementById('editModal').style.display = 'block';
            });
    }
</script>
</body>
</html>