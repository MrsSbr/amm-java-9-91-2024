<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Мои акции</title>
    <style>
        /* Основные стили */
        body {
            font-family: Arial, sans-serif;
            margin: 20px;
        }

        /* Заголовок с кнопкой */
        .header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 20px;
        }

        /* Таблица акций */
        .stock-table {
            width: 100%;
            border-collapse: collapse;
            margin: 20px 0;
        }

        .stock-table th, .stock-table td {
            padding: 12px;
            border: 1px solid #ddd;
            text-align: left;
        }

        .stock-table th {
            background-color: #f8f9fa;
        }

        .stock-table tr:hover {
            background-color: #f1f1f1;
            cursor: pointer;
        }

        .selected {
            background-color: #e3f2fd !important;
        }

        .sell-form {
            margin-top: 20px;
            padding: 20px;
            border: 1px solid #ddd;
            border-radius: 5px;
            display: none;
        }

        .sell-form.visible {
            display: block;
        }

        /* Пагинация */
        .pagination {
            display: flex;
            gap: 10px;
            align-items: center;
            margin-top: 20px;
        }

        .page-btn {
            padding: 8px 16px;
            background-color: #ff3b00;
            color: black;
            border: 1px solid #ddd;
            cursor: pointer;
            border-radius: 4px;
        }

        .page-btn-forward {
            padding: 8px 16px;
            background-color: #00fb00;
            color: black;
            border: 1px solid #ddd;
            cursor: pointer;
            border-radius: 4px;
        }

        .page-btn-forward:disabled {
            background: gray;
            cursor: not-allowed;
            opacity: 0.6;
        }

        .page-btn:disabled {
            background: gray;
            cursor: not-allowed;
            opacity: 0.6;
        }

        /* Кнопки */
        .button {
            background-color: #007bff;
            color: white;
            padding: 10px 20px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            text-decoration: none;
        }

        .button:hover {
            background-color: #0056b3;
        }
    </style>
</head>
<body>
<c:if test="${empty sessionScope.userId}">
    <c:redirect url="/login"/>
</c:if>

<div class="header">
    <h2>Портфель пользователя #${sessionScope.userName}</h2>
    <a href="/login" class="button">Выйти</a>
</div>
<a href="/get-all" class="button">Все акции</a>
<c:choose>
    <c:when test="${empty myStocks}">
        <p>Эта страница пуста.</p>
        <c:if test="${currentPage > 1}">
            <button class="page-btn"
                    onclick="window.location.href='?page=${currentPage - 1}'"
                ${currentPage == 1 ? 'disabled' : ''}
            >
                Назад
            </button>
        </c:if>
    </c:when>
    <c:otherwise>
        <table class="stock-table">
            <thead>
            <tr>
                <th>Название</th>
                <th>Цена</th>
                <th>Количество</th>
                <th>Дивиденды</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${myStocks}" var="stock">
                <tr onclick="selectStock(this)"
                    data-id="${stock.id}"
                    data-max="${stock.count}">
                    <td>${stock.name}</td>
                    <td>${stock.price} ₽</td>
                    <td>${stock.count}</td>
                    <td>${stock.dividends}₽</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>

        <%-- Блок пагинации --%>
        <div class="pagination">
            <button
                    class="page-btn"
                    onclick="window.location.href='?page=${currentPage - 1}'"
                ${currentPage == 1 ? 'disabled' : ''}
            >
                Назад
            </button>

            <span>Страница ${currentPage} из ${totalPages}</span>

            <button
                    class="page-btn-forward"
                    onclick="window.location.href='?page=${currentPage + 1}'"
                ${currentPage == totalPages ? 'disabled' : ''}
            >
                Вперед
            </button>
        </div>
    </c:otherwise>
</c:choose>



<div id="sellForm" class="sell-form">
    <form action="/sell" method="post">
        <input type="hidden" id="stockId" name="stockId">
        <label>
            Количество для продажи:
            <input type="number" id="count" name="count" min="1" required>
        </label>
        <button type="submit" class="button">Продать</button>
    </form>
</div>

<script>
    let selectedRow = null;

    function selectStock(row) {
        if (selectedRow) {
            selectedRow.classList.remove('selected');
        }
        row.classList.add('selected');
        selectedRow = row;

        const form = document.getElementById('sellForm');
        form.classList.add('visible');

        document.getElementById('stockId').value = row.dataset.id;
    }
</script>
</body>
</html>