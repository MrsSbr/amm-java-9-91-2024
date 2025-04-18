<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>История покупок</title>
    <style>
        * {
            box-sizing: border-box;
            font-family: 'Segoe UI', system-ui, sans-serif;
        }

        body {
            margin: 0;
            padding: 2rem;
            min-height: 100vh;
            background: linear-gradient(135deg, #f3e8ff 0%, #f8fafc 100%);
        }

        .header-bar {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 2rem;
        }

        h1 {
            color: #4c1d95;
            margin: 0;
            font-weight: 600;
            font-size: 1.8rem;
        }

        .back-link {
            color: #7c3aed;
            text-decoration: none;
            font-weight: 600;
            padding: 0.5rem 1rem;
            border-radius: 6px;
            transition: all 0.3s ease;
        }

        .back-link:hover {
            background: #f3e8ff;
        }

        table {
            width: 100%;
            border-collapse: collapse;
            background: white;
            border-radius: 16px;
            overflow: hidden;
            box-shadow: 0 8px 24px rgba(106, 33, 182, 0.1);
            margin: 2rem 0;
        }

        th, td {
            padding: 1.2rem;
            text-align: left;
            border-bottom: 1px solid #f1f5f9;
        }

        th {
            background-color: #7c3aed;
            color: white;
            font-weight: 600;
            text-align: center;
        }

        tr:hover {
            background-color: #f8fafc;
        }

        td {
            color: #475569;
            text-align: center;
        }

        .empty-state {
            text-align: center;
            padding: 3rem;
            background: white;
            border-radius: 16px;
            color: #64748b;
            box-shadow: 0 8px 24px rgba(106, 33, 182, 0.1);
            margin-top: 2rem;
        }

        .purchase-details {
            display: flex;
            justify-content: center;
            gap: 0.5rem;
            align-items: center;
        }

        .game-price {
            color: #7c3aed;
            font-weight: 500;
        }
    </style>
</head>
<body>
<div class="header-bar">
    <h1>Ваши покупки</h1>
    <a href="/catalog" class="back-link">← Вернуться в каталог</a>
</div>

<c:choose>
    <c:when test="${empty purchases}">
        <div class="empty-state">
            <p>У вас ещё нет совершенных покупок</p>
        </div>
    </c:when>
    <c:otherwise>
        <table>
            <thead>
            <tr>
                <th>№ заказа</th>
                <th>Игра</th>
                <th>Сумма</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${purchases}" var="purchase">
                <tr>
                    <td>${purchase.orderNumber()}</td>
                    <td>
                        <div class="purchase-details">
                                ${purchase.boardGame().name}
                            <span class="game-price">(${purchase.boardGame().price} руб.)</span>
                        </div>
                    </td>
                    <td>${purchase.payment()} руб.</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </c:otherwise>
</c:choose>
</body>
</html>