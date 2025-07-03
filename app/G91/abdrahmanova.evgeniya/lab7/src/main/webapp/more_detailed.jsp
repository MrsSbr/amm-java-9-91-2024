<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <title>Детали бронирования</title>
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
        }
        body {
            background: #f8f9fa;
            padding: 20px;
            color: #333;
        }
        .btn {
            padding: 12px 24px;
            border: none;
            border-radius: 8px;
            font-size: 16px;
            font-weight: 600;
            cursor: pointer;
            transition: all 0.3s;
        }
        .btn-primary {
            background: #ff7043;
            color: white;
        }
        .btn-primary:hover {
            background: #ff5722;
        }

        .modal-overlay {
            position: fixed;
            top: 0;
            left: 0;
            right: 0;
            bottom: 0;
            background: rgba(0, 0, 0, 0.5);
            display: flex;
            justify-content: center;
            align-items: center;
            z-index: 1000;
            opacity: 0;
            visibility: hidden;
            transition: all 0.3s;
        }
        .modal-overlay.active {
            opacity: 1;
            visibility: visible;
        }
        .modal {
            background: white;
            border-radius: 12px;
            width: 90%;
            max-width: 800px;
            max-height: 90vh;
            overflow-y: auto;
            box-shadow: 0 10px 50px rgba(0, 0, 0, 0.2);
            transform: translateY(20px);
            transition: transform 0.3s;
        }
        .modal-overlay.active .modal {
            transform: translateY(0);
        }
        .modal-header {
            padding: 20px;
            border-bottom: 1px solid #eee;
            display: flex;
            justify-content: space-between;
            align-items: center;
        }
        .modal-title {
            font-size: 22px;
            font-weight: 600;
            color: #ff7043;
        }
        .modal-close {
            background: none;
            border: none;
            font-size: 24px;
            cursor: pointer;
            color: #999;
        }
        .modal-body {
            padding: 30px;
        }
        .detail-grid {
            display: grid;
            grid-template-columns: 1fr 1fr;
            gap: 20px;
        }
        .detail-item {
            margin-bottom: 20px;
        }
        .detail-label {
            font-size: 14px;
            color: #777;
            margin-bottom: 5px;
        }
        .detail-value {
            font-size: 16px;
            font-weight: 500;
        }
        .tour-image {
            width: 100%;
            height: 200px;
            object-fit: cover;
            border-radius: 8px;
            margin-bottom: 20px;
        }
        .status-badge {
            display: inline-block;
            padding: 6px 12px;
            border-radius: 20px;
            font-size: 14px;
            font-weight: 500;
        }
        .status-pending {
            background: #fff8e1;
            color: #ff8f00;
        }
        .status-confirmed {
            background: #e8f5e9;
            color: #2e7d32;
        }
    </style>
</head>
<body>
<button class="btn btn-primary" onclick="showBookingDetails(${booking.id()})">Показать детали бронирования</button>

<div id="bookingModal" class="modal-overlay">
    <div class="modal">
        <div class="modal-header">
            <h2 class="modal-title">Детали бронирования #${booking.id()}</h2>
            <button class="modal-close" onclick="closeModal()">&times;</button>
        </div>
        <div class="modal-body">
            <img src="/images/tours/${booking.tourImage}.jpg" alt="Фото тура" class="tour-image" onerror="this.src='/images/placeholder.jpg'">

            <div class="detail-item">
                <div class="detail-label">Статус</div>
                <div class="status-badge status-${fn:toLowerCase(booking.status().name())}">
                    ${booking.status()}
                </div>
            </div>

            <div class="detail-grid">
                <div class="detail-item">
                    <div class="detail-label">Тур</div>
                    <div class="detail-value">${booking.tourName}</div>
                </div>
                <div class="detail-item">
                    <div class="detail-label">ID бронирования</div>
                    <div class="detail-value">#${booking.id()}</div>
                </div>
                <div class="detail-item">
                    <div class="detail-label">Дата бронирования</div>
                    <div class="detail-value">
                        <fmt:formatDate value="${booking.bookingDate()}" pattern="dd.MM.yyyy HH:mm"/>
                    </div>
                </div>
                <div class="detail-item">
                    <div class="detail-label">Дата тура</div>
                    <div class="detail-value">
                        <fmt:formatDate value="${booking.tourDate()}" pattern="dd.MM.yyyy"/>
                    </div>
                </div>
                <div class="detail-item">
                    <div class="detail-label">Количество участников</div>
                    <div class="detail-value">${booking.countParticipants()} чел.</div>
                </div>
                <div class="detail-item">
                    <div class="detail-label">Сумма</div>
                    <div class="detail-value">
                        <fmt:formatNumber value="${booking.totalPrice()}" type="currency" currencyCode="RUB"/>
                    </div>
                </div>
            </div>

            <div class="detail-item">
                <div class="detail-label">Комментарий</div>
                <div class="detail-value">
                    <c:choose>
                        <c:when test="${not empty booking.comment()}">
                            ${booking.comment()}
                        </c:when>
                        <c:otherwise>
                            Нет комментария
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>

            <div style="margin-top: 30px; display: flex; gap: 15px;">
                <button class="btn btn-primary" onclick="printBooking()">Распечатать</button>
                <button class="btn" onclick="closeModal()" style="background: #f0f0f0;">Закрыть</button>
            </div>
        </div>
    </div>
</div>

<script>
    function showBookingDetails(bookingId) {
        document.getElementById('bookingModal').classList.add('active');
    }

    function closeModal() {
        document.getElementById('bookingModal').classList.remove('active');
    }

    function printBooking() {
        window.print();
    }

    document.getElementById('bookingModal').addEventListener('click', function(e) {
        if (e.target === this) {
            closeModal();
        }
    });
</script>
</body>
</html>
