<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <title>Главная страница</title>
    <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@400;700&display=swap" rel="stylesheet" />
    <style>
        body {
            font-family: 'Roboto', Arial, sans-serif;
            margin: 0;
            padding: 0;
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: #333;
            min-height: 100vh;
            display: flex;
            flex-direction: column;
        }
        .header {
            background-color: #343a40;
            color: white;
            padding: 15px 30px;
            display: flex;
            justify-content: space-between;
            align-items: center;
            box-shadow: 0 2px 6px rgba(0,0,0,0.3);
        }
        .header h1 {
            margin: 0;
            font-weight: 700;
            font-size: 1.8rem;
        }
        .nav-links a, .logout-btn {
            color: white;
            text-decoration: none;
            margin-left: 20px;
            font-weight: 600;
            font-size: 1rem;
            background: none;
            border: none;
            cursor: pointer;
            padding: 6px 12px;
            border-radius: 6px;
            transition: background-color 0.3s ease;
        }
        .nav-links a:hover, .logout-btn:hover {
            background-color: #5a4a8d;
        }
        .container {
            flex: 1;
            padding: 40px 30px;
            max-width: 900px;
            margin: 0 auto;
            background: white;
            border-radius: 12px;
            box-shadow: 0 10px 30px rgba(0,0,0,0.1);
            margin-top: 30px;
            text-align: center;
        }
        .welcome h2 {
            font-weight: 700;
            color: #4a148c;
            margin-bottom: 15px;
        }
        .welcome p {
            font-size: 1.1rem;
            color: #555;
        }
        .actions {
            margin-top: 30px;
        }
        .btn {
            background-color: #7b1fa2;
            color: white;
            font-weight: 700;
            padding: 14px 30px;
            border: none;
            border-radius: 10px;
            cursor: pointer;
            font-size: 1.1rem;
            text-decoration: none;
            display: inline-block;
            transition: background-color 0.3s ease;
            margin: 10px;
        }
        .btn:hover {
            background-color: #4a148c;
        }
        table {
            margin: 20px auto;
            border-collapse: collapse;
            width: 90%;
            max-width: 800px;
        }
        th, td {
            padding: 10px;
            border: 1px solid #ddd;
        }
        th {
            background-color: #7b1fa2;
            color: white;
        }
        tr:nth-child(even) {
            background-color: #f9f9f9;
        }
        @media (max-width: 600px) {
            .header {
                flex-direction: column;
                align-items: flex-start;
            }
            .nav-links {
                margin-top: 10px;
            }
            .nav-links a, .logout-btn {
                margin-left: 0;
                margin-right: 15px;
            }
            .container {
                margin: 20px 15px;
                padding: 30px 20px;
            }
            .btn {
                width: 100%;
                box-sizing: border-box;
            }
            table {
                font-size: 0.9rem;
            }
        }
    </style>
</head>
<body>
<div class="header">
    <h1>Кинотеатр</h1>
    <div class="nav-links">
        <a href="${pageContext.request.contextPath}/films">Фильмы</a>
        <form action="${pageContext.request.contextPath}/logout" method="post" style="display: inline;">
            <button type="submit" class="logout-btn">Выйти</button>
        </form>
    </div>
</div>

<div class="container">
    <div class="welcome">
        <c:choose>
            <c:when test="${not empty sessionScope.email}">
                <h2>Добро пожаловать, ${sessionScope.email}!</h2>
                <c:if test="${not empty tickets}">
                    <h3>Ваши билеты:</h3>
                    <table>
                        <thead>
                        <tr>
                            <th>Фильм</th>
                            <th>Зал</th>
                            <th>Место</th>
                            <th>Начало</th>
                            <th>Окончание</th>
                            <th>Статус</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="ticket" items="${tickets}">
                            <tr>
                                <td>${ticket.filmName}</td>
                                <td>${ticket.hallNumber}</td>
                                <td>${ticket.placeNumber}</td>
                                <td>
                                    <fmt:parseDate value="${ticket.startTime}" pattern="yyyy-MM-dd'T'HH:mm" var="parsedStartDate" />
                                    <fmt:formatDate value="${parsedStartDate}" pattern="dd.MM.yyyy HH:mm" />
                                </td>
                                <td>
                                    <fmt:parseDate value="${ticket.endTime}" pattern="yyyy-MM-dd'T'HH:mm" var="parsedEndDate" />
                                    <fmt:formatDate value="${parsedEndDate}" pattern="dd.MM.yyyy HH:mm" />
                                </td>
                                <td>${ticket.status}</td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </c:if>
                <c:if test="${empty tickets}">
                    <p>У вас пока нет купленных билетов.</p>
                </c:if>
                <p>Выберите раздел в меню для продолжения работы.</p>
                <div class="actions">
                    <a href="${pageContext.request.contextPath}/ticket" class="btn">Купить билет</a>
                    <a href="${pageContext.request.contextPath}/films" class="btn">Просмотреть фильмы</a>
                </div>
            </c:when>
            <c:otherwise>
                <h2>Добро пожаловать!</h2>
                <p>Пожалуйста, <a href="${pageContext.request.contextPath}/signin">войдите</a> в систему, чтобы продолжить.</p>
            </c:otherwise>
        </c:choose>
    </div>
</div>
</body>
</html>