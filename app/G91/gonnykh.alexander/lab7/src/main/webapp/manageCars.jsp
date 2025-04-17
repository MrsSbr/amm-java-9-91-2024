<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Manage Cars</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f4;
        }
        .container {
            max-width: 900px;
            margin: 40px auto;
            padding: 30px;
            background-color: white;
            border-radius: 12px;
            box-shadow: 0 0 12px rgba(0, 0, 0, 0.1);
        }
        h2 {
            text-align: center;
            margin-bottom: 30px;
        }
        .add-form, .car-card {
            margin-bottom: 25px;
            padding: 20px;
            border-radius: 10px;
            border: 1px solid #ccc;
            background-color: #fafafa;
        }
        input, select {
            width: 100%;
            padding: 10px;
            margin-top: 10px;
            margin-bottom: 15px;
            border: 1px solid #ccc;
            border-radius: 6px;
        }
        button {
            padding: 10px 20px;
            border: none;
            border-radius: 6px;
            cursor: pointer;
        }
        .save {
            background-color: #4CAF50;
            color: white;
        }
        .cancel {
            background-color: #f44336;
            color: white;
            margin-left: 10px;
        }
        .edit {
            background-color: #1976D2;
            color: white;
            text-decoration: none;
        }
        .delete {
            background-color: #f44336;
            color: white;
        }
        .car-actions {
            margin-top: 15px;
            text-align: right;
        }
        .error, .success {
            text-align: center;
            margin-bottom: 20px;
        }
        .error { color: red; }
        .success { color: green; font-weight: bold; }
    </style>
</head>
<body>
<div class="container">
    <h2>Car Management</h2>

    <c:if test="${not empty errorMessage}">
        <div class="error">${errorMessage}</div>
    </c:if>

    <form class="add-form" id="addForm" method="post" action="${pageContext.request.contextPath}/addCar">
        <input type="text" name="manufacturer" placeholder="Manufacturer" required />
        <input type="text" name="model" placeholder="Model" required />
        <input type="number" name="year" placeholder="Year" required />
        <select name="status" required>
            <option value="AVAILABLE">Available</option>
            <option value="BROKEN">Broken</option>
        </select>
        <select name="carClass" required>
            <option value="ECONOMY">Economy</option>
            <option value="COMFORT">Comfort</option>
            <option value="COMFORT_PLUS">Comfort Plus</option>
            <option value="BUSINESS">Business</option>
        </select>
        <div class="car-actions">
            <button type="submit" class="save">Save</button>
            <button type="reset" class="cancel">Cancel</button>
        </div>
    </form>

    <c:forEach var="car" items="${cars}">
        <div class="car-card" id="car-${car.id}">
            <strong>${car.manufacturer} ${car.model}</strong><br/>
            Year: ${car.year}<br/>
            Status: ${car.status}<br/>
            Class: ${car.carClass}<br/>
            <div class="car-actions">
                <a href="${pageContext.request.contextPath}/editCar?carId=${car.id}" class="edit">Edit</a>
                <form method="post" action="${pageContext.request.contextPath}/deleteCar" style="display:inline;">
                    <input type="hidden" name="carId" value="${car.id}" />
                    <button type="submit" class="delete" onclick="return confirm('Are you sure you want to delete this car?')">Delete</button>
                </form>
            </div>
        </div>
    </c:forEach>
</div>
</body>
</html>
