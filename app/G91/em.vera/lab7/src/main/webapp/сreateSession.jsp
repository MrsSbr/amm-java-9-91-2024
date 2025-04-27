<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Запись на сессию</title>
    <style>
        /* Ваши стили здесь… */
        body { font-family: sans-serif; padding: 2rem; background: #f2f2f7; }
        .card { background: #fff; padding: 2rem; border-radius: 1rem; max-width: 400px; margin: auto; box-shadow: 0 4px 12px rgba(0,0,0,0.1);}
        .card h2 { margin-bottom: 1rem; }
        .field { margin-bottom: 1rem; }
        .field label { display: block; margin-bottom: 0.25rem; }
        .field input { width: 100%; padding: 0.5rem; border: 1px solid #ccc; border-radius: 0.25rem; }
        .actions { text-align: right; }
        .actions button { padding: 0.5rem 1rem; border: none; border-radius: 0.25rem; background: #4a00e0; color: #fff; cursor: pointer; }
    </style>
</head>
<body>
<div class="card">
    <h2>Запись на сессию</h2>

    <c:if test="${not empty errorMessage}">
        <div style="color: #c0392b; margin-bottom: 1rem;">
                ${errorMessage}
        </div>
    </c:if>

    <form method="post" action="${pageContext.request.contextPath}/session/create">
        <!-- Скрытое поле с логином психолога -->
        <input type="hidden" name="login" value="${psychologistLogin}" />

        <div class="field">
            <label for="date">Дата</label>
            <input id="date" type="date" name="date" required />
        </div>

        <div class="field">
            <label for="duration">Длительность (минуты)</label>
            <input id="duration" type="number" name="duration" min="1" required />
        </div>

        <div class="actions">
            <button type="submit">Записаться</button>
        </div>
    </form>
</div>
</body>
</html>
