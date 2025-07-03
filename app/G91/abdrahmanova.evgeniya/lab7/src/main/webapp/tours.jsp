<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Доступные туры</title>
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
        }
        .actions {
            display: flex;
            gap: 15px;
        }
        body {
            background: #f8f9fa;
            padding: 30px 20px;
            color: #333;
        }

        .container {
            max-width: 1400px;
            margin: 0 auto;
        }

        .header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 40px;
            padding: 25px 30px;
            background: white;
            border-radius: 12px;
            box-shadow: 0 6px 25px rgba(0, 0, 0, 0.08);
        }

        .page-title {
            font-size: 32px;
            font-weight: 700;
            display: flex;
            align-items: center;
            gap: 15px;
        }

        .filters {
            display: flex;
            gap: 25px;
            align-items: center;
        }

        .filter-group {
            display: flex;
            flex-direction: column;
        }

        .filter-label {
            margin-bottom: 8px;
            font-weight: 500;
            color: #555;
        }

        .search-input {
            padding: 14px 20px;
            border: 1px solid #ddd;
            border-radius: 8px;
            font-size: 16px;
            width: 300px;
            transition: all 0.3s;
        }

        .search-input:focus {
            outline: none;
            border-color: #0aa;
            box-shadow: 0 0 0 3px rgba(0, 170, 170, 0.1);
        }

        .price-slider-container {
            display: flex;
            align-items: center;
            gap: 15px;
            width: 300px;
        }

        .price-input {
            width: 150px;
            padding: 14px;
            border: 1px solid #ddd;
            border-radius: 8px;
            font-size: 16px;
            text-align: center;
        }

        .btn {
            padding: 14px 28px;
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
            transform: translateY(-3px);
            box-shadow: 0 5px 15px rgba(0, 0, 0, 0.1);
        }

        .tours-container {
            display: grid;
            grid-template-columns: repeat(2, 1fr);
            gap: 35px;
        }

        .input-group {
            flex: 1;
            text-align: left;
            margin-bottom: 0;
        }

        .tour-card {
            background: white;
            border-radius: 12px;
            box-shadow: 0 8px 30px rgba(0, 0, 0, 0.1);
            overflow: hidden;
            transition: transform 0.3s ease, box-shadow 0.3s ease;
            display: flex;
            flex-direction: column;
        }

        .tour-card:hover {
            transform: translateY(-10px);
            box-shadow: 0 12px 40px rgba(0, 0, 0, 0.15);
        }

        .tour-photo {
            height: 280px;
            background: #f5f5f5;
            display: flex;
            align-items: center;
            justify-content: center;
            overflow: hidden;
        }

        .tour-photo-placeholder {
            font-size: 120px;
            color: #e0e0e0;
        }

        .tour-content {
            padding: 30px;
            flex: 1;
            display: flex;
            flex-direction: column;
        }

        .tour-header {
            display: flex;
            justify-content: space-between;
            align-items: flex-start;
            margin-bottom: 20px;
        }

        .tour-title {
            font-size: 24px;
            font-weight: 700;
            color: #222;
            max-width: 70%;
        }

        .tour-price {
            font-size: 28px;
            font-weight: 700;
            background: #f0f7ff;
            padding: 8px 16px;
            border-radius: 8px;
        }

        .tour-description {
            color: #666;
            line-height: 1.6;
            margin-bottom: 25px;
            flex: 1;
        }

        .tour-meta {
            display: flex;
            gap: 25px;
            margin-bottom: 25px;
        }

        .meta-item {
            display: flex;
            align-items: center;
            gap: 8px;
            font-size: 16px;
        }

        .meta-icon {
            font-size: 20px;
        }

        .tour-footer {
            display: flex;
            justify-content: space-between;
            align-items: center;
            border-top: 1px solid #eee;
            padding-top: 25px;
            margin-top: auto;
        }

        .tour-language {
            font-size: 16px;
            font-weight: 500;
        }

        .btn-tour-actions {
            padding: 12px 28px;
            border-radius: 8px;
            font-size: 16px;
            font-weight: 600;
            cursor: pointer;
            transition: all 0.3s;
        }

        .btn-tour-actions:hover {
            transform: scale(1.05);
        }

        .palette-1 .page-title { color: #0aa; }
        .palette-1 .btn-bookings { background: #0aa; color: white; }
        .palette-1 .btn-bookings:hover { background: #099; }
        .palette-1 .tour-price { color: #0aa; }
        .palette-1 .btn-tour-actions { background: #0aa; color: white; }
        .palette-1 .btn-tour-actions:hover { background: #099; }

        .palette-3 .page-title { color: #ff7043; }
        .palette-3 .btn-bookings { background: #ff7043; color: white; }
        .palette-3 .btn-bookings:hover { background: #ff5722; }
        .palette-3 .tour-price { color: #ff7043; }
        .palette-3 .btn-tour-actions { background: #ff7043; color: white; }
        .palette-3 .btn-tour-actions:hover { background: #ff5722; }

        /* Адаптивность */
        @media (max-width: 1200px) {
            .tours-container {
                gap: 25px;
            }
        }

        @media (max-width: 992px) {
            .tours-container {
                grid-template-columns: 1fr;
            }

            .header {
                flex-direction: column;
                gap: 25px;
            }

            .filters {
                flex-wrap: wrap;
                justify-content: center;
            }
        }

        @media (max-width: 768px) {
            .search-input {
                width: 100%;
            }

            .price-slider-container {
                width: 100%;
            }

            .tour-header {
                flex-direction: column;
                gap: 15px;
            }

            .tour-title {
                max-width: 100%;
            }

            .tour-meta {
                flex-wrap: wrap;
            }
        }
        .booking-form {
            display: flex;
            flex-direction: column;
            gap: 15px;
            width: 100%;
        }

        .booking-fields {
            display: flex;
            gap: 15px;
            align-items: flex-end;
        }

        .booking-input-group {
            display: flex;
            flex-direction: column;
            flex: 1;
        }

        .booking-input-group label {
            font-size: 14px;
            color: #666;
            margin-bottom: 5px;
            font-weight: 500;
        }

        .booking-date-input,
        .booking-participants-input {
            padding: 12px 15px;
            border: 1px solid #e0e0e0;
            border-radius: 8px;
            font-size: 16px;
            transition: all 0.3s;
            width: 100%;
        }

        .booking-date-input:focus,
        .booking-participants-input:focus {
            outline: none;
            border-color: #0aa;
            box-shadow: 0 0 0 3px rgba(0, 170, 170, 0.1);
        }

        .booking-submit-btn {
            padding: 12px 20px;
            white-space: nowrap;
        }
    </style>
</head>
<body>
<div class="container palette-3">
    <div class="header">
        <h1 class="page-title">
            <span>🌍</span> Доступные туры
        </h1>

        <div class="filters">
            <div class="filter-group">
                <label class="filter-label">Поиск по названию</label>
                <input
                        type="text"
                        class="search-input"
                        id="searchInput"
                        placeholder="Введите название тура">
            </div>

            <div class="filter-group">
                <label class="filter-label">Максимальная цена</label>
                <div class="price-slider-container">
                    <input
                            type="number"
                            class="price-input"
                            id="priceFilter"
                            placeholder="До руб."
                            min="0">
                </div>
            </div>
        </div>
        <div class="actions">
            <c:if test="${sessionScope.user.userRole().name() eq 'GUIDE'}">
                <button class="btn btn-bookings" onclick="location.href='create_tour'">
                    <span>✚</span> Создать тур
                </button>
            </c:if>
            <c:if test="${sessionScope.user.userRole().name() eq 'USER'}">
                <button class="btn btn-bookings" onclick="location.href='bookings'">
                    <span>📋</span> Мои бронирования
                </button>
            </c:if>
        </div>
    </div>

    <div class="tours-container" id="toursContainer">
        <c:forEach items="${requestScope.tours}" var="tour">
            <div class="tour-card"
                 data-title="${tour.title()}"
                 data-price="${tour.price()}">
                <div class="tour-photo">
                    <div class="tour-photo-placeholder">🏔️</div>
                </div>

                <div class="tour-content">
                    <div class="tour-header">
                        <h2 class="tour-title">${tour.title()}</h2>
                        <div class="tour-price">${tour.price()} ₽</div>
                    </div>

                    <p class="tour-description">
                            ${tour.description()}
                    </p>

                    <div class="tour-meta">
                        <div class="meta-item">
                            <span class="meta-icon">⏱️</span>
                            <span>${tour.duration()} часов</span>
                        </div>

                        <div class="meta-item">
                            <span class="meta-icon">👥</span>
                            <span>До ${tour.maxParticipants()} чел.</span>
                        </div>

                        <div class="meta-item">
                            <span class="meta-icon">📍</span>
                            <span>${tour.startLocation()}</span>
                        </div>

                        <div class="tour-language">
                            <span class="meta-icon">🗣️</span>
                            <span>${tour.languages().name().toLowerCase()}</span>
                        </div>
                    </div>

                    <div class="tour-footer booking-fields">
                        <c:if test="${sessionScope.user.userRole().name() eq 'USER'}">
                            <!-- <button
                                    class="btn-tour-actions"
                                    onclick="location.href='${pageContext.request.contextPath}/booking-details?id=${booking.id()}'">
                                Подробнее
                            </button> -->
                            <c:if test="${requestScope.booked_tours.contains(tour.id())}">
                                <span class="btn-tour-actions">Забронировано</span>
                            </c:if>
                            <c:if test="${not requestScope.booked_tours.contains(tour.id())}">
                                <form id="${tour.id()}_booking_form" action="${pageContext.request.contextPath}/book_tour" method="POST">
                                    <div class="booking-fields">
                                        <div class="booking-input-group">
                                            <label for="${tour.id()}_tour_date_input">Дата</label>
                                            <input class="booking-date-input"
                                                   type="date"
                                                   id="${tour.id()}_tour_date_input"
                                                   name="date"
                                                   required>
                                        </div>

                                        <div class="booking-input-group">
                                            <label for="${tour.id()}_tour_participants_input">Участники</label>
                                            <input class="booking-participants-input"
                                                   type="number"
                                                   id="${tour.id()}_tour_participants_input"
                                                   name="participants"
                                                   min="1"
                                                   max="${tour.maxParticipants()}"
                                                   required
                                                   value="1">
                                        </div>

                                        <input type="hidden" id="${tour.id()}_tour_id_input" name="tour_id" value="${tour.id()}">

                                        <button type="submit" class="btn-tour-actions booking-submit-btn">
                                            <span>Забронировать</span>
                                        </button>
                                    </div>
                                </form>
                            </c:if>
                        </c:if>
                    </div>
                </div>
            </div>
        </c:forEach>
    </div>
</div>

<script>
    function filterTours() {
        const searchTerm = document.getElementById('searchInput').value.toLowerCase();
        const maxPrice = parseFloat(document.getElementById('priceFilter').value) || 1000000;

        const tourCards = document.querySelectorAll('.tour-card');

        tourCards.forEach(card => {
            const title = card.dataset.title;
            const price = parseFloat(card.dataset.price);

            const matchesSearch = title.includes(searchTerm);
            const matchesPrice = price <= maxPrice;

            if (matchesSearch && matchesPrice) {
                card.style.display = 'flex';
            } else {
                card.style.display = 'none';
            }
        });
    }

    document.getElementById('searchInput').addEventListener('input', filterTours);
    document.getElementById('priceFilter').addEventListener('input', filterTours);

    document.addEventListener('DOMContentLoaded', filterTours);
</script>
</body>
</html>