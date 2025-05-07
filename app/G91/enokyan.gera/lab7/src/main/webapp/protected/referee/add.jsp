<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ include file="../../header.jspf" %>
<!doctype html>
<html lang="en" data-bs-theme="auto">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Добавить игру</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.5/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-body-tertiary">
<main class="container" style="width: 40%">
    <div class="w-50 mx-auto">
        <c:if test="${not empty param.success}">
            <div class="alert alert-success alert-dismissible fade show mb-2" role="alert">
                Партия добавлена!
                <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
            </div>
        </c:if>
        <c:if test="${not empty param.error}">
            <div class="alert alert-danger alert-dismissible fade show mb-2" role="alert">
                <c:choose>
                    <c:when test="${param.error == 'samePlayer'}">
                        Выбран один и тот же игрок!
                    </c:when>
                    <c:otherwise>
                        Неизвестная ошибка!
                    </c:otherwise>
                </c:choose>
                <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
            </div>
        </c:if>
        <div class="card">
            <form method="post" class="card-body mb-0">
                <select class="form-select mb-3" name="player1" id="player1" required>
                    <option value="">Первый игрок</option>
                    <c:forEach var="player" items="${requestScope.players}">
                        <option value="${player.id}">${player.nickname}</option>
                    </c:forEach>
                </select>
                <select class="form-select mb-3" name="player2" id="player2" required>
                    <option value="">Второй игрок</option>
                    <c:forEach var="player" items="${requestScope.players}">
                        <option value="${player.id}">${player.nickname}</option>
                    </c:forEach>
                </select>
                <select class="form-select mb-3" id="result" name="result" required>
                    <option value="">Выберите результат</option>
                    <option value="FirstPlayerWon">Победа первого</option>
                    <option value="Draw">Ничья</option>
                    <option value="SecondPlayerWon">Победа второго</option>
                </select>
                <button type="submit" class="btn btn-primary d-block mx-auto">Добавить партию</button>
            </form>
        </div>
    </div>
</main>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.5/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>