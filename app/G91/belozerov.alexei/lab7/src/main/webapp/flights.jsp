<%@ page language="java"
         pageEncoding="UTF-8"
         contentType="text/html; charset=UTF-8"
         import="ru.vsu.amm.java.utils.ApplicationConstants" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="ru">
<head>
  <meta charset="UTF-8">
  <title>Доступные рейсы</title>

  <!-- Bootstrap 5 -->
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">

<div class="container py-5">

  <h2 class="mb-4">Доступные рейсы</h2>

  <!-- Сообщение, если список пуст -->
  <c:if test="${empty flights}">
    <div class="alert alert-info">На данный момент рейсов нет.</div>
  </c:if>

  <!-- Таблица рейсов -->
  <c:if test="${not empty flights}">
    <div class="table-responsive">
      <table class="table table-hover align-middle table-bordered">
        <thead class="table-light">
        <tr>
          <th>№ рейса</th>
          <th>Откуда</th>
          <th>Куда</th>
          <th>Вылет</th>
          <th>Прилёт</th>
          <th>Самолёт</th>
          <th>Всего&nbsp;мест</th>
          <th>Цена</th>
          <th></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="f" items="${flights}">
          <tr>
            <td>${f.flightId}</td>
            <td>${f.origin}</td>
            <td>${f.destination}</td>
            <td>${f.departureTime}</td>
            <td>${f.arrivalTime}</td>
            <td>${f.airplaneModel}</td>
            <td>${f.capacity}</td>
            <td>${f.price}</td>
            <td class="text-center">
              <a class="btn btn-sm btn-outline-primary"
                 href="${pageContext.request.contextPath}<%= ApplicationConstants.AVAILABLE_BOOKINGS_URL %>?flightId=${f.flightId}">
                Выбрать&nbsp;место
              </a>
            </td>
          </tr>
        </c:forEach>
        </tbody>
      </table>
    </div>
  </c:if>

</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
