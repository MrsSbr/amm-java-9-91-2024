<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ include file="header.jspf" %>
<!doctype html>
<html lang="en" data-bs-theme="auto">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Последние игры</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.5/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-body-tertiary">
<main class="container" style="width: 40%">
    <ul class="list-group w-50 mx-auto">
        <c:forEach var="game" items="${requestScope.games}">
            <li class="list-group-item">
                <a href="${pageContext.request.contextPath}/game/${game.id}" class="d-flex justify-content-between align-items-center text-decoration-none text-body">
                    <c:choose>
                        <c:when test="${game.result == 0.0}">
                            <span class="col-1 d-flex justify-content-center">🏆</span>
                        </c:when>
                        <c:when test="${game.result == 0.5}">
                            <span class="col-1 d-flex justify-content-center">🤝</span>
                        </c:when>
                        <c:otherwise>
                            <span class="col-1 d-flex justify-content-center"></span>
                        </c:otherwise>
                    </c:choose>
                    <span class="col-4 d-flex justify-content-end">${game.firstPlayersNickname}</span>
                    <span class="col-2 d-flex justify-content-center">vs</span>
                    <span class="col-4 d-flex justify-content-start">${game.secondPlayersNickname}</span>
                    <c:choose>
                        <c:when test="${game.result == 1.0}">
                            <span class="col-1 d-flex justify-content-center">🏆</span>
                        </c:when>
                        <c:when test="${game.result == 0.5}">
                            <span class="col-1 d-flex justify-content-center">🤝</span>
                        </c:when>
                        <c:otherwise>
                            <span class="col-1 d-flex justify-content-center"></span>
                        </c:otherwise>
                    </c:choose>
                </a>
            </li>
        </c:forEach>
    </ul>
</main>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.5/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>