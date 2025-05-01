<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Active Trips</title>
    <style>
        body {
            font-family: "Segoe UI", Tahoma, Geneva, Verdana, sans-serif;
            background-color: #f4f6f8;
            margin: 0;
            padding: 0;
        }

        .top-bar {
            background-color: #2196F3;
            color: white;
            display: flex;
            justify-content: space-between;
            align-items: center;
            padding: 15px 30px;
            box-shadow: 0 2px 6px rgba(0, 0, 0, 0.1);
        }

        .top-bar h1 {
            margin: 0;
            font-size: 24px;
        }

        .login-button {
            position: absolute;
            top: 20px;
            right: 40px;
            background-color: #1976D2;
            color: white;
            font-weight: bold;
            padding: 8px 16px;
            text-decoration: none;
            border-radius: 6px;
            transition: background-color 0.3s ease, transform 0.2s ease;

        }

        .login-button:active {
            transform: scale(0.98);
        }

        .main-content {
            padding: 30px;
        }

        table {
            width: 100%;
            border-collapse: collapse;
            background-color: white;
            border-radius: 8px;
            box-shadow: 0 2px 8px rgba(0, 0, 0, 0.05);
            overflow: hidden;
        }

        th, td {
            padding: 12px 16px;
            text-align: left;
            border-bottom: 1px solid #eee;
        }

        th {
            background-color: #f0f0f0;
            color: #333;
            font-weight: 600;
        }

        tr:hover {
            background-color: #f9f9f9;
        }

        button {
            padding: 8px 14px;
            background-color: #1976d2;
            color: white;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            transition: background-color 0.2s ease-in-out;
        }

        button:hover {
            background-color: #1565c0;
        }

        .centered {
            text-align: center;
        }

        .back-button {
            margin-top: 30px;
            display: flex;
            justify-content: center;
        }
    </style>

</head>
<body>
<div class="top-bar">
    <h1>Active Trips</h1>
    <a href="${pageContext.request.contextPath}/login" class="login-button">Login</a>
</div>

<div class="main-content">
    <table>
        <thead>
        <tr>
            <th>Model</th>
            <th>Manufacturer</th>
            <th>Start Date</th>
            <th>End Date</th>
            <th>Total Price</th>
            <th class="centered">Action</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="trip" items="${trips}">
            <tr>
                <td>${trip.carModel}</td>
                <td>${trip.carManufacturer}</td>
                <td>
                    <c:set var="start" value="${trip.startTrip}"/>
                    <c:out value="${fn:substring(start, 0, 10)} ${fn:substring(start, 11, 16)}"/>
                </td>
                <td>
                    <c:choose>
                        <c:when test="${trip.endTrip != null}">
                            <c:set var="end" value="${trip.endTrip}"/>
                            <c:out value="${fn:substring(end, 0, 10)} ${fn:substring(end, 11, 16)}"/>
                        </c:when>
                        <c:otherwise>In progress</c:otherwise>
                    </c:choose>
                </td>
                <td>${trip.finalPrice != null ? trip.finalPrice : 'In progress'}</td>
                <td class="centered">
                    <form action="finishTrip" method="post">
                        <input type="hidden" name="tripId" value="${trip.id}">
                        <button type="submit">Finish</button>
                    </form>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

    <div class="back-button">
        <form action="${pageContext.request.contextPath}/availableCars" method="get">
            <button type="submit">Back to Available Cars</button>
        </form>
    </div>
</div>
</body>
</html>
