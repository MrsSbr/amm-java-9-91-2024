<%@ page language="java"
         pageEncoding="UTF-8"
         contentType="text/html; charset=UTF-8"
         import="ru.vsu.amm.java.utils.ApplicationConstants" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core"       prefix="c"  %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions"  prefix="fn" %>
<!DOCTYPE html>
<html lang="ru">
<head>
  <meta charset="UTF-8">
  <title>Выбор места</title>

  <!-- Bootstrap 5 -->
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">

  <style>
    .seats-grid {
      display: grid;
      grid-template-columns: repeat(10, 60px);   /* 10 колонок */
      grid-gap: 8px;
      justify-content: center;                   /* центр по горизонтали */
    }
    .seat-btn    { width: 60px; height: 60px; padding: 0; }
    .seat-number { font-size: .8rem; }
  </style>
</head>
<body class="bg-light">

<div class="container py-5 text-center">

  <h2 class="mb-4">Выберите место</h2>

  <!-- ===== СЕТКА 10 × 10 A1…J10 ===== -->
  <div class="seats-grid mx-auto">
    <c:set var="letters" value="A,B,C,D,E,F,G,H,I,J"/>
    <c:forEach var="row" items="${fn:split(letters, ',')}">
      <c:forEach var="col" begin="1" end="10">
        <c:set var="code" value="${row}${col}"/>
        <c:choose>
          <c:when test="${availableSeats.contains(code)}">
            <button type="button" class="btn btn-success seat-btn"
                    data-seat="${code}" onclick="openModal(this)">
              <span class="seat-number">${code}</span>
            </button>
          </c:when>
          <c:otherwise>
            <button type="button" class="btn btn-secondary seat-btn" disabled>
              <span class="seat-number">${code}</span>
            </button>
          </c:otherwise>
        </c:choose>
      </c:forEach>
    </c:forEach>
  </div>
</div>

<!-- ===== МОДАЛЬНОЕ ОКНО ПОДТВЕРЖДЕНИЯ ===== -->
<div class="modal fade" id="confirmModal" tabindex="-1" aria-hidden="true">
  <div class="modal-dialog">
    <form class="modal-content"
          action="${pageContext.request.contextPath}<%= ApplicationConstants.BOOK_URL %>"
          method="post">

      <div class="modal-header">
        <h5 class="modal-title">Подтвердите бронь</h5>
        <button type="button" class="btn-close" data-bs-dismiss="modal"
                aria-label="Close"></button>
      </div>

      <div class="modal-body">
        <p>Вы выбрали место <strong id="chosenSeat"></strong>.</p>
        <p>Номер билета: <strong id="generatedTicket"></strong></p>

        <!-- скрытые поля для POST -->
        <input type="hidden" id="seatNumberInput"   name="seatNumber">
        <input type="hidden" id="ticketNumberInput" name="ticketNumber">
      </div>

      <div class="modal-footer">
        <button type="button" class="btn btn-secondary"
                data-bs-dismiss="modal">Отмена</button>
        <button type="submit" class="btn btn-primary">Забронировать</button>
      </div>
    </form>
  </div>
</div>

<!-- Bootstrap JS + скрипт генерации билета -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
<script>
  const modal = new bootstrap.Modal(document.getElementById('confirmModal'));

  // генератор случайного билета (8 символов A-Z / 0-9)
  function randomTicket(len = 15) {
    const chars = 'ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789';
    let t = '';
    for (let i = 0; i < len; i++) {
      t += chars.charAt(Math.floor(Math.random() * chars.length));
    }
    return t;
  }

  // открытие модалки
  function openModal(btn) {
    const seat   = btn.dataset.seat;
    const ticket = randomTicket();

    document.getElementById('chosenSeat').textContent      = seat;
    document.getElementById('generatedTicket').textContent = ticket;

    document.getElementById('seatNumberInput').value   = seat;
    document.getElementById('ticketNumberInput').value = ticket;

    modal.show();
  }
</script>
</body>
</html>
