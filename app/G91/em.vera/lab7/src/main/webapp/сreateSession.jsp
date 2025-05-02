<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Запись на сессию</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/flatpickr/dist/flatpickr.min.css">
    <style>
        :root {
            --gradient-start: #8e2de2;
            --gradient-end:   #4a00e0;
            --bg-card:        #ffffff;
            --text-dark:      #333333;
            --text-light:     #f5f5f5;
            --input-bg:       #f2f2f7;
            --border-color:   #ddd;
        }
        * { box-sizing: border-box; margin: 0; padding: 0; }
        body {
            background: #f2f2f7;
            font-family: "Segoe UI", Tahoma, Geneva, Verdana, sans-serif;
            color: var(--text-dark);
            display: flex;
            align-items: center;
            justify-content: center;
            min-height: 100vh;
        }
        .card {
            width: 360px;
            background: var(--bg-card);
            border-radius: 1rem;
            box-shadow: 0 4px 16px rgba(0,0,0,0.1);
            overflow: hidden;
        }
        .card-header {
            background: linear-gradient(135deg, var(--gradient-start), var(--gradient-end));
            padding: 2rem 1.5rem;
            text-align: center;
        }
        .card-header h2 {
            color: var(--text-light);
            margin-bottom: 0.25rem;
            font-size: 1.6rem;
        }
        .card-header p {
            color: rgba(255,255,255,0.85);
            font-size: 0.9rem;
        }
        .card-body {
            padding: 2rem 1.5rem;
        }
        .card-body form {
            display: flex;
            flex-direction: column;
            gap: 1rem;
        }
        .input-group {
            position: relative;
        }
        .input-group input {
            width: 100%;
            padding: 0.75rem 1rem 0.75rem 2.5rem;
            border: 1px solid var(--border-color);
            border-radius: 0.5rem;
            background: var(--input-bg);
            font-size: 0.95rem;
            color: var(--text-dark);
        }
        .input-group .icon {
            position: absolute;
            top: 50%;
            left: 0.75rem;
            transform: translateY(-50%);
            font-size: 1rem;
            color: #888;
        }
        .btn {
            width: 100%;
            padding: 0.75rem;
            border: none;
            border-radius: 0.5rem;
            background: linear-gradient(135deg, var(--gradient-start), var(--gradient-end));
            color: white;
            font-size: 1rem;
            font-weight: bold;
            cursor: pointer;
            transition: opacity 0.3s;
        }
        .btn:hover {
            opacity: 0.9;
        }
    </style>
</head>
<body>
<div class="card">
    <div class="card-header">
        <h2>Записаться на сессию</h2>
        <p>Психолог: <strong>${psychologistLogin}</strong></p>
    </div>
    <div class="card-body">
        <form action="${pageContext.request.contextPath}/session/create" method="post">
            <!-- Передаём логин психолога скрытым полем -->
            <input type="hidden" name="login" value="${psychologistLogin}" />

            <div class="input-group">
                <span class="icon">&#128197;</span>
                <input id="datePicker" name="date" placeholder="Выберите дату" required />
            </div>
            <div class="input-group">
                <span class="icon">&#9201;</span>
                <input type="number" name="duration" placeholder="Длительность (мин)" min="1" required />
            </div>

            <button type="submit" class="btn">Записаться</button>
        </form>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/flatpickr"></script>
<script>
    // Инициализируем flatpickr с выключением занятых дат
    flatpickr("#datePicker", {
        dateFormat: "Y-m-d",
        minDate: "today",
        disable: [
            <c:forEach items="${busyDates}" var="d" varStatus="status">
            '${d}'<c:if test="${!status.last}">,</c:if>
            </c:forEach>
        ]
    });
</script>
</body>
</html>
