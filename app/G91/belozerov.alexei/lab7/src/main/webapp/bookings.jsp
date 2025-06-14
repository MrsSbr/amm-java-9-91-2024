<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="ru">
<head>
  <meta charset="UTF-8">
  <title>Мои бронирования</title>
  <style>
    body {font-family: Arial, sans-serif;background:#f5f5f5;}
    .container {width:90%;margin:40px auto;}
    table {width:100%;border-collapse:collapse;background:white;}
    th,td {padding:12px;border:1px solid #ddd;text-align:center;}
    th {background:#007bff;color:#fff;}
    a.btn {display:inline-block;padding:10px 20px;background:#28a745;color:#fff;border-radius:4px;text-decoration:none;margin-top:20px;}
    a.btn:hover{background:#218838;}
  </style>
</head>
<body>
<div class="container">
  <h2>Мои бронирования</h2>
  <c:choose>
    <c:when test="${empty bookings}">
      <p>У вас пока нет бронирований.</p>
    </c:when>
    <c:otherwise>
      <table>
        <tr>
          <th>Номер билета</th>
          <th>Место</th>
          <th>ID рейса</th>
        </tr>
        <c:forEach items="${bookings}" var="b">
          <tr>
            <td><c:out value="${b.ticketNumber}"/></td>
            <td><c:out value="${b.seatNumber}"/></td>
            <td><c:out value="${b.flightId}"/></td>
          </tr>
        </c:forEach>
      </table>
    </c:otherwise>
  </c:choose>
  <a href="${pageContext.request.contextPath}/flights" class="btn">Смотреть рейсы</a>
</div>
</body>
</html>