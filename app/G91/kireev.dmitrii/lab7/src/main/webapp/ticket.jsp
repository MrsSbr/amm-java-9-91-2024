<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <title>Покупка билета</title>
    <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@400;700&display=swap" rel="stylesheet" />
    xml
    <style>
        /* Сброс и базовые настройки */
        * {
            box-sizing: border-box;
        }
        body {
            font-family: 'Roboto', sans-serif;
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            margin: 0;
            padding: 0;
            min-height: 100vh;
            display: flex;
            justify-content: center;
            align-items: center;
            color: #333;
        }
        .ticket-container {
            background: #fff;
            padding: 40px 50px;
            border-radius: 15px;
            box-shadow: 0 15px 30px rgba(0,0,0,0.2);
            max-width: 480px;
            width: 100%;
            text-align: center;
            transition: transform 0.3s ease;
        }
        .ticket-container:hover {
            transform: translateY(-5px);
            box-shadow: 0 20px 40px rgba(0,0,0,0.3);
        }
        h1 {
            margin-bottom: 30px;
            color: #4a148c;
            font-weight: 700;
            font-size: 2.4rem;
            letter-spacing: 1px;
        }
        form {
            display: flex;
            flex-direction: column;
            gap: 22px;
            text-align: left;
        }
        label {
            font-weight: 600;
            margin-bottom: 6px;
            color: #555;
            display: block;
            font-size: 1rem;
        }
        select, input[type="number"], input[type="datetime-local"] {
            padding: 14px 18px;
            border: 2px solid #ddd;
            border-radius: 10px;
            font-size: 1rem;
            transition: border-color 0.3s ease, box-shadow 0.3s ease;
            width: 100%;
            outline: none;
        }
        select:focus, input[type="number"]:focus, input[type="datetime-local"]:focus {
            border-color: #7b1fa2;
            box-shadow: 0 0 10px rgba(123, 31, 162, 0.5);
        }
        button {
            background-color: #7b1fa2;
            color: white;
            font-weight: 700;
            padding: 16px;
            border: none;
            border-radius: 12px;
            cursor: pointer;
            font-size: 1.2rem;
            letter-spacing: 0.05em;
            transition: background-color 0.3s ease, box-shadow 0.3s ease;
            margin-top: 10px;
        }
        button:hover {
            background-color: #4a148c;
            box-shadow: 0 8px 20px rgba(74, 20, 140, 0.6);
        }
        /* Адаптивность */
        @media (max-width: 520px) {
            .ticket-container {
                padding: 30px 25px;
                max-width: 90%;
            }
            h1 {
                font-size: 1.8rem;
            }
            button {
                font-size: 1rem;
                padding: 14px;
            }
        }
    </style>
</head>
<body>
<div class="ticket-container">
    <h1>Покупка билета на фильм</h1>

    <form action="${pageContext.request.contextPath}/ticket" method="POST" novalidate>
        <label for="filmId">Выберите фильм:</label>
        <select id="filmId" name="filmId" required>
            <option value="" disabled selected>-- Выберите фильм --</option>
            <c:forEach var="film" items="${films}">
                <option value="${film.filmId}">${film.name}</option>
            </c:forEach>
        </select>

        <label for="hallNumber">Номер зала:</label>
        <input type="number" id="hallNumber" name="hallNumber" required placeholder="Введите номер зала" min="1" />

        <label for="placeNumber">Номер места:</label>
        <input type="number" id="placeNumber" name="placeNumber" required placeholder="Введите номер места" min="1" />

        <label for="startTime">Время начала:</label>
        <input type="datetime-local" id="startTime" name="startTime" required />

        <label for="endTime">Время окончания:</label>
        <input type="datetime-local" id="endTime" name="endTime" required />

        <button type="submit">Купить билет</button>
    </form>
</div>
</body>
</html>