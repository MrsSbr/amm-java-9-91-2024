<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
    <title>Мои бронирования</title>
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
        .container {
            max-width: 1200px;
            margin: 0 auto;
        }
        .header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 30px;
            padding: 20px;
            background: white;
            border-radius: 12px;
            box-shadow: 0 4px 20px rgba(0, 0, 0, 0.08);
        }
        .user-info {
            display: flex;
            align-items: center;
            gap: 15px;
        }
        .user-avatar {
            width: 50px;
            height: 50px;
            border-radius: 50%;
            background: #f0f0f0;
            display: flex;
            align-items: center;
            justify-content: center;
            font-size: 24px;
        }
        .user-name {
            font-weight: 600;
            font-size: 18px;
        }
        .user-email {
            color: #777;
            font-size: 14px;
        }
        .actions {
            display: flex;
            gap: 15px;
        }
        .btn {
            padding: 12px 24px;
            border: none;
            border-radius: 8px;
            font-size: 16px;
            font-weight: 600;
            cursor: pointer;
            transition: all 0.3s;
            display: flex;
            align-items: center;
            gap: 8px;
        }
        .btn:hover {
            transform: translateY(-2px);
            box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
        }
        .bookings-container {
            background: white;
            border-radius: 12px;
            box-shadow: 0 6px 30px rgba(0, 0, 0, 0.1);
            overflow: hidden;
        }
        .section-title {
            padding: 25px 30px;
            font-size: 22px;
            font-weight: 600;
            border-bottom: 1px solid #eee;
            display: flex;
            align-items: center;
            gap: 12px;
        }
        .booking-list {
            padding: 0;
        }
        .booking-item {
            display: flex;
            border-bottom: 1px solid #f0f0f0;
            padding: 25px 30px;
            transition: background 0.3s;
        }
        .booking-item:hover {
            background: #fafafa;
        }
        .booking-icon {
            width: 60px;
            height: 60px;
            border-radius: 12px;
            display: flex;
            align-items: center;
            justify-content: center;
            font-size: 28px;
            margin-right: 20px;
            flex-shrink: 0;
        }
        .booking-details {
            flex: 1;
        }
        .booking-title {
            font-weight: 600;
            font-size: 18px;
            margin-bottom: 5px;
            display: flex;
            align-items: center;
            gap: 10px;
        }
        .booking-meta {
            display: flex;
            gap: 25px;
            margin-top: 15px;
            flex-wrap: wrap;
        }
        .meta-item {
            display: flex;
            flex-direction: column;
        }
        .meta-label {
            font-size: 13px;
            color: #888;
            margin-bottom: 4px;
        }
        .meta-value {
            font-weight: 500;
        }
        .booking-status {
            padding: 6px 15px;
            border-radius: 20px;
            font-size: 14px;
            font-weight: 500;
            align-self: flex-start;
        }
        .booking-actions {
            display: flex;
            flex-direction: column;
            gap: 10px;
            min-width: 150px;
        }
        .action-btn {
            padding: 8px 15px;
            border: 1px solid #e0e0e0;
            border-radius: 6px;
            background: white;
            cursor: pointer;
            transition: all 0.2s;
            text-align: center;
            font-size: 14px;
        }
        .action-btn:hover {
            background: #f5f5f5;
        }
        .empty-state {
            padding: 60px 30px;
            text-align: center;
            color: #888;
        }
        .empty-icon {
            font-size: 64px;
            margin-bottom: 20px;
            opacity: 0.3;
        }
        .toast-container {
            position: fixed;
            bottom: 30px;
            right: 30px;
            z-index: 1000;
            display: flex;
            flex-direction: column;
            gap: 15px;
        }
        .toast {
            padding: 18px 25px;
            border-radius: 10px;
            box-shadow: 0 6px 20px rgba(0, 0, 0, 0.15);
            display: flex;
            align-items: center;
            max-width: 350px;
            transform: translateX(110%);
            transition: transform 0.4s cubic-bezier(0.34, 1.56, 0.64, 1);
        }
        .toast.show {
            transform: translateX(0);
        }
        .toast-icon {
            font-size: 24px;
            margin-right: 15px;
        }
        .toast-content {
            flex: 1;
        }
        .toast-title {
            font-weight: 600;
            margin-bottom: 4px;
        }
        .toast-message {
            font-size: 14px;
            color: #555;
            white-space: pre-line;
        }
        .toast-close {
            background: none;
            border: none;
            font-size: 20px;
            cursor: pointer;
            color: #999;
            margin-left: 15px;
            transition: color 0.2s;
        }
        .toast-close:hover {
            color: #333;
        }
        .status-pending {
            background: #fff8e1;
            color: #ff8f00;
        }
        .status-confirmed {
            background: #e8f5e9;
            color: #2e7d32;
        }
        .status-cancelled {
            background: #ffebee;
            color: #c62828;
        }
        .status-completed {
            background: #e3f2fd;
            color: #1565c0;
        }
        .status-refunded {
            background: #f3e5f5;
            color: #7b1fa2;
        }
        .status-failed {
            background: #fbe9e7;
            color: #d84315;
        }
        .palette-2 .user-avatar { background: #fff3e0; color: #ef6c00; }
        .palette-2 .btn-primary { background: #ff7043; color: white; }
        .palette-2 .btn-primary:hover { background: #ff5722; }
        .palette-2 .btn-outline { background: white; border: 1px solid #ff7043; color: #ff7043; }
        .palette-2 .btn-outline:hover { background: #fff3e0; }
        .palette-2 .section-title { color: #ff7043; }
        .palette-2 .booking-icon { background: #fff3e0; color: #ff7043; }
        .palette-2 .toast { background: #fff0eb; border-left: 5px solid #ff7043; }
        .palette-2 .toast-icon { color: #ff7043; }
    </style>
</head>
<body>
<div class="container palette-2">
    <div class="header">
        <div class="user-info">
            <div>
                <div class="user-name">${sessionScope.user.fullName()}</div>
                <div class="user-email">${sessionScope.user.eMail()}</div>
            </div>
        </div>
        <div class="actions">
            <button class="btn btn-primary" onclick="location.href='tours'">
                <span>🗺️</span> Найти новый тур
            </button>
<%--            <button class="btn btn-outline" onclick="location.href='new-booking'">--%>
<%--                <span>✚</span> Новое бронирование--%>
<%--            </button>--%>
        </div>
    </div>

    <!-- Список бронирований -->
    <div class="bookings-container">
        <div class="section-title">
            <span>📋</span> Мои бронирования
        </div>

        <c:choose>
            <c:when test="${not empty requestScope.bookings}">
                <div class="booking-list">
                    <c:forEach items="${requestScope.bookings}" var="booking" varStatus="loop">
                        <c:if test="${booking != null}">
                            <div class="booking-item">
                                <div class="booking-icon">🏨🎫📅💼</div>
                                <div class="booking-details">
                                    <div class="booking-title">
                                        Бронь #${booking.id()}
                                        <div class="booking-status status-${fn:toLowerCase(booking.status().name())}">
                                                ${booking.status()}
                                        </div>
                                    </div>

                                    <div class="booking-meta">
                                        <!-- ID тура -->
                                        <div class="meta-item">
                                            <div class="meta-label">Тур</div>
                                            <div class="meta-value">
                                                <c:choose>
                                                    <c:when test="${booking.idTour() != null}">
                                                        #${booking.idTour()}
                                                    </c:when>
                                                    <c:otherwise>
                                                        —
                                                    </c:otherwise>
                                                </c:choose>
                                            </div>
                                        </div>

                                        <div class="meta-item">
                                            <div class="meta-label">Дата бронирования</div>
                                            <div class="meta-value">
                                                <c:choose>
                                                    <c:when test="${booking.date() != null}">
                                                        ${booking.date().toString()} <!-- Вывод без форматирования -->
                                                    </c:when>
                                                    <c:otherwise>
                                                        Не указана
                                                    </c:otherwise>
                                                </c:choose>
                                            </div>
                                        </div>

                                        <div class="meta-item">
                                            <div class="meta-label">Участники</div>
                                            <div class="meta-value">
                                                <c:choose>
                                                    <c:when test="${booking.countParticipants() != null}">
                                                        ${booking.countParticipants()} чел.
                                                    </c:when>
                                                    <c:otherwise>
                                                        Не указано
                                                    </c:otherwise>
                                                </c:choose>
                                            </div>
                                        </div>

                                        <div class="meta-item">
                                            <div class="meta-label">Сумма</div>
                                            <div class="meta-value">
                                                <c:choose>
                                                    <c:when test="${booking.totalPrice() != null}">
                                                        ${booking.totalPrice()} ₽
                                                    </c:when>
                                                    <c:otherwise>
                                                        Не указана
                                                    </c:otherwise>
                                                </c:choose>
                                            </div>
                                        </div>
                                    </div>
                                </div>

                                <div class="booking-actions">
                                    <c:if test="${booking.status() != null
                                                    && booking.status().name() != 'CANCELLED'
                                                    && booking.status().name() != 'COMPLETED'
                                                    && booking.status().name() != 'FAILED'
                                                    && booking.status().name() != 'REFUNDED'}">
                                        <button class="action-btn" onclick="cancelBooking(${booking.id()})">
                                            Отменить
                                        </button>
                                    <!-- </c:if>
                                    <button class="action-btn" onclick="viewDetails(${booking.id()})">
                                        Подробнее
                                    </button> -->
                                </div>
                            </div>
                        </c:if>
                    </c:forEach>
                </div>
            </c:when>
            <c:otherwise>
                <div class="empty-state">
                    <div class="empty-icon">🌎</div>
                    <h3>У вас пока нет бронирований</h3>
                    <p>Начните с поиска интересного тура и добавьте первую бронь</p>
                </div>
            </c:otherwise>
        </c:choose>
    </div>
</div>

<div class="toast-container"></div>

<script>
    function showToast(message, title = 'Ошибка', type = 'error') {
        const toastContainer = document.querySelector('.toast-container');
        const toast = document.createElement('div');
        toast.className = `toast ${type}-toast`;

        const icons = {
            error: '⚠️',
            success: '✅',
            warning: '⚠️',
            info: 'ℹ️'
        };

        toast.innerHTML = `
                <div class="toast-icon">${icons[type] || '⚠️'}</div>
                <div class="toast-content">
                    <div class="toast-title">${title}</div>
                    <div class="toast-message">${message}</div>
                </div>
                <button class="toast-close">&times;</button>
            `;

        toastContainer.appendChild(toast);

        setTimeout(() => {
            toast.classList.add('show');
        }, 10);

        setTimeout(() => {
            hideToast(toast);
        }, 5000);

        toast.querySelector('.toast-close').addEventListener('click', () => {
            hideToast(toast);
        });
    }

    function hideToast(toast) {
        toast.classList.remove('show');
        setTimeout(() => {
            toast.remove();
        }, 400);
    }

    function checkBackendError() {
        const errorMessage = "${requestScope.errorMessage}";

        if (errorMessage && errorMessage.trim() !== "") {
            showToast(errorMessage, 'Ошибка операции', 'error');

            fetch('${pageContext.request.contextPath}/clear-error', {
                method: 'POST'
            }).catch(error => {
                console.error('Ошибка при очистке сообщения:', error);
            });
        }
    }

    document.addEventListener('DOMContentLoaded', checkBackendError);

    function viewDetails(bookingId) {
        location.href = "/tours?id=${bookingId}";
    }

    function cancelBooking(bookingId) {
        if (confirm('Вы уверены, что хотите отменить бронирование?')) {
            fetch('cancel-booking?id=' + bookingId, {
                method: 'POST'
            })
                .then(response => {
                    if (response.ok) {
                        showToast('Бронирование успешно отменено', 'Успех', 'success');
                        setTimeout(() => location.reload(), 2000);
                    } else {
                        response.text().then(text => {
                            showToast(text || 'Ошибка при отмене бронирования', 'Ошибка', 'error');
                        });
                    }
                })
                .catch(error => {
                    showToast('Ошибка сети: ' + error.message, 'Ошибка', 'error');
                });
        }
    }
</script>
</body>
</html>