<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <title>Ваши заказы</title>
    <style>
        * {
            box-sizing: border-box;
            font-family: 'Helvetica', Arial, sans-serif;
        }

        body {
            margin: 0;
            min-height: 100vh;
            background-color: #0f172a;
            background-image:
                    radial-gradient(circle at 20% 30%, rgba(59, 130, 246, 0.15) 0%, transparent 25%),
                    radial-gradient(circle at 80% 70%, rgba(59, 130, 246, 0.15) 0%, transparent 25%),
                    linear-gradient(to bottom right, #0f172a, #1e293b);
            color: #e2e8f0;
            position: relative;
            padding-top: 70px;
        }

        body::before {
            content: "";
            position: absolute;
            top: 0;
            left: 0;
            right: 0;
            bottom: 0;
            background:
                    linear-gradient(90deg, rgba(59, 130, 246, 0.05) 1px, transparent 1px),
                    linear-gradient(rgba(59, 130, 246, 0.05) 1px, transparent 1px);
            background-size: 40px 40px;
            opacity: 0.5;
            pointer-events: none;
        }

        .container {
            background: rgba(30, 41, 59, 0.9);
            padding: 2rem;
            border-radius: 16px;
            box-shadow: 0 8px 32px rgba(30, 58, 138, 0.4);
            width: 95%;
            max-width: 1000px;
            margin: 20px auto;
            border: 1px solid rgba(59, 130, 246, 0.2);
            backdrop-filter: blur(8px);
        }

        h1 {
            text-align: center;
            color: #60a5fa;
            margin-bottom: 2rem;
            font-weight: 600;
            font-size: 1.8rem;
            letter-spacing: -0.5px;
        }

        table {
            width: 100%;
            border-collapse: collapse;
            margin: 1rem 0;
            background: rgba(30, 41, 59, 0.7);
        }

        th, td {
            padding: 1rem;
            border-bottom: 1px solid #334155;
            text-align: left;
        }

        th {
            background-color: rgba(37, 99, 235, 0.3);
            color: #93c5fd;
            font-weight: 600;
        }

        tr:hover {
            background-color: rgba(59, 130, 246, 0.1);
        }

        .summary {
            margin: 2rem 0;
            text-align: center;
        }

        .total-cost {
            font-size: 1.4rem;
            color: #60a5fa;
            font-weight: 600;
        }

        .button-pay {
            display: block;
            width: 200px;
            margin: 2rem auto 0;
            padding: 1rem;
            background-color: #2563eb;
            color: white;
            border: none;
            border-radius: 8px;
            font-size: 1rem;
            font-weight: 600;
            cursor: pointer;
            transition: all 0.3s ease;
            text-transform: uppercase;
        }

        .button-pay:hover {
            background-color: #1d4ed8;
            transform: translateY(-2px);
            box-shadow: 0 4px 12px rgba(29, 78, 216, 0.3);
        }

        .navbar {
            position: fixed;
            top: 0;
            left: 0;
            width: 100%;
            background-color: rgba(30, 41, 59, 0.95);
            padding: 1rem 2rem;
            display: flex;
            justify-content: center;
            gap: 2rem;
            box-shadow: 0 2px 10px rgba(0, 0, 0, 0.3);
            z-index: 1000;
            border-bottom: 1px solid rgba(59, 130, 246, 0.2);
            backdrop-filter: blur(8px);
        }

        .navbar a {
            color: #93c5fd;
            text-decoration: none;
            font-weight: 600;
            font-size: 1rem;
            position: relative;
            transition: color 0.3s ease;
        }

        .navbar a::after {
            content: '';
            position: absolute;
            bottom: -4px;
            left: 0;
            width: 0;
            height: 2px;
            background: #60a5fa;
            transition: width 0.3s ease;
        }

        .navbar a:hover {
            color: #60a5fa;
        }

        .navbar a:hover::after {
            width: 100%;
        }

        @media (max-width: 768px) {
            .container {
                padding: 1rem;
                width: 98%;
            }

            th, td {
                padding: 0.8rem;
                font-size: 0.9rem;
            }

            .navbar {
                gap: 1rem;
                padding: 1rem;
            }

            .button-pay {
                width: 100%;
            }
        }
    </style>
</head>
<body>
<div class="navbar">
    <a href="/catalog">Каталог</a>
    <a href="/order?isPaid=false">Корзина</a>
    <a href="/order?isPaid=true">История заказов</a>
    <a href="/logout">Выход</a>
</div>

<div class="container">
    <c:choose>
        <c:when test="${isPaid}">
            <h1>История заказов</h1>
        </c:when>
        <c:otherwise>
            <h1>Корзина</h1>
        </c:otherwise>
    </c:choose>

    <div class="summary">
        <p class="total-cost">Общая сумма: ${ordersResponse.cost} $</p>
    </div>

    <table>
        <thead>
        <tr>
            <th>Бренд</th>
            <th>Модель</th>
            <th>Цвет</th>
            <th>Цена ($)</th>
            <th>Дата заказа</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="order" items="${ordersResponse.orders}">
            <c:set var="smartphone" value="${ordersResponse.usedSmartphones[order.smartphoneId]}" />
            <tr>
                <td>${smartphone.brand}</td>
                <td>${smartphone.model}</td>
                <td>${smartphone.color}</td>
                <td>${smartphone.price}</td>
                <td>${order.registrationDate}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

    <c:if test="${not empty ordersResponse.orders && not isPaid}">
        <form action="/order/pay" method="post">
            <button class="button-pay" type="submit">Оплатить корзину</button>
        </form>
    </c:if>
</div>
</body>
</html>