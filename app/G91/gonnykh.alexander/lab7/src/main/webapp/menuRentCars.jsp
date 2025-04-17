<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="ru.vsu.amm.java.enums.Status" %>
<html>
<head>
    <title>Available Cars</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f4;
            margin: 0;
            padding: 0;
        }

        .header {
            background-color: #2196F3;
            color: white;
            padding: 20px 40px;
            text-align: center;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
        }

        .container {
            display: flex;
            justify-content: space-between;
            padding: 40px;
        }

        .sidebar {
            width: 250px;
            background: #2196F3;
            color: white;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
        }

        .sidebar a {
            display: block;
            margin-bottom: 15px;
            padding: 12px;
            text-align: center;
            text-decoration: none;
            background-color: #1976D2;
            border-radius: 8px;
            color: white;
            font-weight: bold;
        }

        .sidebar a:hover {
            background-color: #1565C0;
        }

        .content {
            flex-grow: 1;
            padding: 20px;
            max-width: 800px;
        }

        .car-card {
            background: white;
            border: 1px solid #ccc;
            border-radius: 12px;
            margin-bottom: 20px;
            padding: 20px;
            box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
            transition: transform 0.3s ease;
        }

        .car-card:hover {
            transform: scale(1.02);
        }

        .car-card h3 {
            margin-top: 0;
            font-size: 22px;
            color: #333;
        }

        .car-card p {
            font-size: 16px;
            color: #555;
        }

        .book-btn {
            display: inline-block;
            margin-top: 15px;
            padding: 10px 20px;
            background-color: #4CAF50;
            color: white;
            text-decoration: none;
            border: none;
            border-radius: 6px;
            font-size: 16px;
            cursor: pointer;
            transition: background-color 0.3s ease;
        }

        .book-btn:hover {
            background-color: #45a049;
        }

        .error {
            color: red;
            text-align: center;
            margin-bottom: 20px;
        }

        .success {
            color: green;
            text-align: center;
            margin-bottom: 20px;
            font-size: 18px;
        }

        .no-cars {
            text-align: center;
            color: #888;
            font-size: 18px;
        }
    </style>
</head>
<body>
<div class="header">
    <h1>Available Cars</h1>
</div>
<div class="container">
    <div class="sidebar">
        <a href="${pageContext.request.contextPath}/trips">Current Trips</a>
    </div>

    <div class="content">

        <c:if test="${not empty errorMessage}">
            <div class="error">${errorMessage}</div>
        </c:if>
        <div id="successMsg" class="success"></div>

        <c:forEach var="car" items="${cars}">
            <div class="car-card" id="car-${car.id}">
                <h3>${car.manufacturer} ${car.model}</h3>
                <p><strong>Year:</strong> ${car.year}</p>
                <p><strong>Status:</strong>
                    <c:choose>
                        <c:when test="${car.status == 'AVAILABLE'}">Available</c:when>
                        <c:when test="${car.status == 'RENTED'}">Booked</c:when>
                        <c:otherwise>${car.status}</c:otherwise>
                    </c:choose>
                </p>
                <p><strong>Class:</strong>
                    <c:choose>
                        <c:when test="${car.carClass == 'ECONOMY'}">Economy</c:when>
                        <c:when test="${car.carClass == 'COMFORT'}">Comfort</c:when>
                        <c:when test="${car.carClass == 'COMFORT_PLUS'}">Comfort Plus</c:when>
                        <c:when test="${car.carClass == 'BUSINESS'}">Business</c:when>
                        <c:otherwise>${car.carClass}</c:otherwise>
                    </c:choose>
                </p>

                <form action="${pageContext.request.contextPath}/bookCar" method="post" class="bookCarForm">
                    <input type="hidden" name="carId" value="${car.id}"/>
                    <input type="hidden" name="userId" value="${user.id}"/> <!-- Added user ID here -->
                    <input type="submit" class="book-btn" value="Book Now"/>
                </form>
            </div>
        </c:forEach>

        <c:if test="${empty cars}">
            <p class="no-cars">No available cars at the moment.</p>
        </c:if>
    </div>
</div>

<script>
    document.addEventListener("DOMContentLoaded", function () {
        const successMsg = document.getElementById("successMsg");

        function attachFormHandlers() {
            document.querySelectorAll("form.bookCarForm").forEach(form => {
                form.addEventListener("submit", function (e) {
                    e.preventDefault();

                    const formData = new FormData(form);
                    const url = form.action;

                    fetch(url, {
                        method: form.method,
                        body: formData
                    })
                        .then(response => {
                            if (!response.ok) {
                                throw new Error("Network response was not ok");
                            }
                            return response.text();
                        })
                        .then(data => {
                            successMsg.innerText = "Successfully booked the car";
                            refreshCarList();
                        })
                        .catch(error => {
                            console.error("Booking error:", error);
                            alert("Booking failed. Please try again.");
                        });
                });
            });
        }

        function refreshCarList() {
            fetch("${pageContext.request.contextPath}/availableCars", {
                method: "GET",
                headers: {
                    "X-Requested-With": "XMLHttpRequest"
                }
            })
                .then(response => {
                    if (!response.ok) {
                        throw new Error("Failed to fetch car list");
                    }
                    return response.text();
                })
                .then(html => {
                    const parser = new DOMParser();
                    const doc = parser.parseFromString(html, "text/html");
                    const newContent = doc.querySelector(".content");
                    const currentContent = document.querySelector(".content");

                    if (newContent && currentContent) {
                        currentContent.innerHTML = newContent.innerHTML;
                        attachFormHandlers();
                    }
                })
                .catch(error => {
                    console.error("Failed to update car list:", error);
                });
        }

        attachFormHandlers();
        setInterval(refreshCarList, 5000);
    });
</script>

</body>
</html>
