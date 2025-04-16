<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Аренда</title>
    <style>
        body { font-family: Arial; padding: 20px; }
        h1 { color: #4682b4; }
        section { margin-bottom: 20px; }
        div { border: 1px solid #ddd; padding: 10px; margin-bottom: 10px; }
        button { background: #4682b4; color: white; border: none; padding: 5px 10px; }
        input { margin: 5px 0; }
        .error {
            color: red;
            font-weight: bold;
            margin: 10px 0;
            padding: 10px;
            border: 1px solid red;
            background-color: #ffeeee;
        }
    </style>
</head>
<body>
<h1>Аренда объектов</h1>
<a href="${pageContext.request.contextPath}/home"><button>На главную</button></a>

<c:if test="${not empty sessionScope.errorMessage}">
    <div class="error">${sessionScope.errorMessage}</div>
    <c:remove var="errorMessage" scope="session"/>
</c:if>

<section>
    <h2>Предметы</h2>
    <c:forEach items="${items}" var="item">
        <div>
            <h3>${item.objectName}</h3>
            <p>${item.price} руб./сутки</p>
            <form action="createAgreement" method="post">
                <input type="hidden" name="objectId" value="${item.objectID}">
                Начало: <input type="date" name="startDate" required><br>
                Конец: <input type="date" name="endDate" required><br>
                <button type="submit">Арендовать</button>
            </form>
        </div>
    </c:forEach>
</section>

<section>
    <h2>Мероприятия</h2>
    <c:forEach items="${events}" var="event">
        <div>
            <h3>${event.objectName}</h3>
            <p>${event.price} руб./сутки</p>
            <form action="createAgreement" method="post">
                <input type="hidden" name="objectId" value="${event.objectID}">
                Начало: <input type="date" name="startDate" required><br>
                Конец: <input type="date" name="endDate" required><br>
                <button type="submit">Арендовать</button>
            </form>
        </div>
    </c:forEach>
</section>

<section>
    <h2>Жилье</h2>
    <c:forEach items="${habitations}" var="habitation">
        <div>
            <h3>${habitation.objectName}</h3>
            <p>${habitation.price} руб./сутки</p>
            <form action="createAgreement" method="post">
                <input type="hidden" name="objectId" value="${habitation.objectID}">
                Начало: <input type="date" name="startDate" required><br>
                Конец: <input type="date" name="endDate" required><br>
                <button type="submit">Арендовать</button>
            </form>
        </div>
    </c:forEach>
</section>
</body>
</html>