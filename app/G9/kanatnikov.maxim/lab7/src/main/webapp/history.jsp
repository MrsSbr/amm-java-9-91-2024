<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>История покупок</title>
    <style>
        table {
            width: 100%;
            border-collapse: collapse;
        }
        th, td {
            padding: 12px;
            border-bottom: 1px solid #ddd;
            text-align: center;
        }
        th {
            background: #2196F3;
            color: white;
        }
    </style>
</head>
<body>
<h1>Ваши покупки</h1>
<a href="/catalog">Вернуться в каталог</a>

<c:choose>
    <c:when test="${empty purchases}">
        <p>У вас ещё нет покупок</p>
    </c:when>
    <c:otherwise>
        <table>
            <tr>
                <th>№ заказа</th>
                <th>Игра</th>
                <th>Сумма</th>
            </tr>
            <c:forEach items="${purchases}" var="purchase">
                <tr>
                    <td>${purchase.orderNumber()}</td>
                    <td>${purchase.boardGame().name} (${purchase.boardGame().price} руб.)</td>
                    <td>${purchase.payment()} руб.</td>
                </tr>
            </c:forEach>
        </table>
    </c:otherwise>
</c:choose>
</body>
</html>