<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
  String flightIdParam = request.getParameter("flightId");
  if (flightIdParam != null) {
    session.setAttribute("flightId", flightIdParam);
    response.sendRedirect(request.getContextPath() + "/available-bookings");
    return;
  }
%>
<!DOCTYPE html>
<html lang="ru">
<head>
  <meta charset="UTF-8">
  <title>Доступные рейсы</title>
  <style>
    body {font-family: Arial, sans-serif;background:#f5f5f5;}
    .container {width:95%;margin:40px auto;}
    table {width:100%;border-collapse:collapse;background:white;}
    th,td {padding:12px;border:1px solid #ddd;text-align:center;}
    th {background:#17a2b8;color:#fff;}
    a.select{color:#007bff;text-decoration:none;}
    a.select:hover{text-decoration:underline;}
  </style>
</head>
<body>
<div class="container">
  <h2>Предстоящие рейсы</h2>
  <c:choose>
    <c:when test="${empty flights}">
      <p>Нет доступных рейсов.</p>
    </c:when>
    <c:otherwise>
      <table>
        <tr>
          <th>ID</th>
          <th>Рейс</th>
          <th>Действие</th>
        </tr>
        <c:forEach items="${flights}" var="f">
          <tr>
            <td><c:out value="${f.id}"/></td>
            <td>
              <!-- Печатаем toString() объекта, если другой информации нет -->
              <c:out value="${f}"/>
            </td>
            <td>
              <a class="select" href="flights.jsp?flightId=${f.id}">Выбрать</a>
            </td>
          </tr>
        </c:forEach>
      </table>
    </c:otherwise>
  </c:choose>
  <p><a href="${pageContext.request.contextPath}/bookings">Назад к бронированиям</a></p>
</div>
</body>
</html>