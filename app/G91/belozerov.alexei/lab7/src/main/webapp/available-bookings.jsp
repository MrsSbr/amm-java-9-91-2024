<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
  /* --- Обработка выбора места (seat) --- */
  String seatParam = request.getParameter("seat");
  if (seatParam != null) {
    session.setAttribute("seatNumber", seatParam);
    // Генерируем простой псевдо-номер билета
    String ticketNumber = java.util.UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    session.setAttribute("ticketNumber", ticketNumber);
    response.sendRedirect(request.getContextPath() + "/book");
    return;
  }
%>
<!DOCTYPE html>
<html lang="ru">
<head>
  <meta charset="UTF-8">
  <title>Выбор места</title>
  <style>
    body {font-family: Arial, sans-serif;background:#f5f5f5;}
    .container {width:800px;margin:40px auto;background:white;padding:20px;border-radius:8px;box-shadow:0 0 10px rgba(0,0,0,.1);}
    .seats{display:grid;grid-template-columns:repeat(6,1fr);gap:10px;}
    a.seat{display:block;padding:10px;background:#28a745;color:#fff;text-align:center;border-radius:4px;text-decoration:none;}
    a.seat:hover{background:#218838;}
    span.taken{display:block;padding:10px;background:#6c757d;color:#fff;text-align:center;border-radius:4px;}
  </style>
</head>
<body>
<div class="container">
  <h2>Свободные места</h2>
  <c:choose>
    <c:when test="${empty availableSeats}">
      <p>На выбранный рейс нет свободных мест.</p>
    </c:when>
    <c:otherwise>
      <div class="seats">
        <c:forEach items="${availableSeats}" var="s">
          <a class="seat" href="available-bookings.jsp?seat=${s}">
            <c:out value="${s}"/>
          </a>
        </c:forEach>
      </div>
    </c:otherwise>
  </c:choose>
  <p><a href="${pageContext.request.contextPath}/flights">Назад к рейсам</a></p>
</div>
</body>
</html>
