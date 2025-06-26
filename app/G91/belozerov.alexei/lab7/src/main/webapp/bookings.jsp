<%@ page language="java"
         pageEncoding="UTF-8"
         contentType="text/html; charset=UTF-8"
         import="ru.vsu.amm.java.utils.ApplicationConstants" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Мои бронирования</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">

<div class="container py-5">

    <!-- Заголовок + кнопки -->
    <div class="d-flex justify-content-between align-items-center mb-4">
        <h2 class="m-0">Мои бронирования</h2>

        <div class="d-flex gap-2">
            <!-- переход к доступным рейсам -->
            <a class="btn btn-outline-primary"
               href="${pageContext.request.contextPath}<%= ApplicationConstants.FLIGHTS_URL %>">
                Доступные рейсы
            </a>

            <!-- кнопка «Выйти» -->
            <form action="${pageContext.request.contextPath}/logout" method="post" class="m-0 p-0">
                <button type="submit" class="btn btn-outline-danger">
                    Выйти
                </button>
            </form>
        </div>
    </div>

    <!-- Если бронирований нет -->
    <c:if test="${empty bookings}">
        <div class="alert alert-info">Вы ещё не забронировали ни одного билета.</div>
    </c:if>

    <!-- Таблица бронирований -->
    <c:if test="${not empty bookings}">
        <div class="table-responsive">
            <table class="table table-bordered table-hover align-middle">
                <thead class="table-light">
                <tr>
                    <th>ID брони</th>
                    <th>№ билета</th>
                    <th>Место</th>
                    <th>ID рейса</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="b" items="${bookings}">
                    <tr>
                        <td>${b.bookingId}</td>
                        <td>${b.ticketNumber}</td>
                        <td>${b.seatNumber}</td>
                        <td>${b.flightId}</td>
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
