<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ include file="header.jspf" %>
<!doctype html>
<html lang="en" data-bs-theme="auto">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Игра ${requestScope.game.id}</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.5/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-body-tertiary">
<main class="container" style="width: 40%">
    <div class="w-50 mx-auto">
        <c:if test="${not empty param.success}">
            <div class="alert alert-success alert-dismissible fade show mb-2" role="alert">
                Партия аннулирована!
                <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
            </div>
        </c:if>
        <c:if test="${not empty param.error}">
            <div class="alert alert-danger alert-dismissible fade show mb-2" role="alert">
                <c:choose>
                    <c:when test="${param.error == 'cantDeleteGame'}">
                        Действие невозможно!
                    </c:when>
                    <c:otherwise>
                        Неизвестная ошибка!
                    </c:otherwise>
                </c:choose>
                <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
            </div>
        </c:if>
        <div class="card">
            <div class="card-header d-flex justify-content-between align-items-center bg-primary text-white">
                <c:choose>
                    <c:when test="${requestScope.game.result == 0.0}">
                        <span class="col-1 d-flex justify-content-center">🏆</span>
                    </c:when>
                    <c:when test="${requestScope.game.result == 0.5}">
                        <span class="col-1 d-flex justify-content-center">🤝</span>
                    </c:when>
                    <c:otherwise>
                        <span class="col-1 d-flex justify-content-center"></span>
                    </c:otherwise>
                </c:choose>
                <span class="col-4 d-flex justify-content-end">${requestScope.game.firstPlayersNickname}</span>
                <span class="col-2 d-flex justify-content-center">vs</span>
                <span class="col-4 d-flex justify-content-start">${requestScope.game.secondPlayersNickname}</span>
                <c:choose>
                    <c:when test="${requestScope.game.result == 1.0}">
                        <span class="col-1 d-flex justify-content-center">🏆</span>
                    </c:when>
                    <c:when test="${requestScope.game.result == 0.5}">
                        <span class="col-1 d-flex justify-content-center">🤝</span>
                    </c:when>
                    <c:otherwise>
                        <span class="col-1 d-flex justify-content-center"></span>
                    </c:otherwise>
                </c:choose>
            </div>
            <div class="card-body">
                <p class="text-muted mb-1 text-center">
                    Рейтинги
                </p>
                <div class="d-flex mb-3">
                    <div class="col-6 text-muted text-center">
                        <fmt:formatNumber value="${requestScope.game.firstPlayersRatingBefore}" type="number" maxFractionDigits="0"/>
                        <c:choose>
                            <c:when test="${requestScope.game.firstPlayersRatingChange >= 0}">
                                <span class="text-success">+<fmt:formatNumber value="${requestScope.game.firstPlayersRatingChange}" type="number" maxFractionDigits="0"/></span>
                            </c:when>
                            <c:otherwise>
                                <span class="text-danger"><fmt:formatNumber value="${requestScope.game.firstPlayersRatingChange}" type="number" maxFractionDigits="0"/></span>
                            </c:otherwise>
                        </c:choose>
                    </div>
                    <div class="col-6 text-muted text-center">
                        <fmt:formatNumber value="${requestScope.game.secondPlayersRatingBefore}" type="number" maxFractionDigits="0"/>
                        <c:choose>
                            <c:when test="${requestScope.game.secondPlayersRatingChange >= 0}">
                                <span class="text-success">+<fmt:formatNumber value="${requestScope.game.secondPlayersRatingChange}" type="number" maxFractionDigits="0"/></span>
                            </c:when>
                            <c:otherwise>
                                <span class="text-danger"><fmt:formatNumber value="${requestScope.game.secondPlayersRatingChange}" type="number" maxFractionDigits="0"/></span>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>
                <p class="text-muted mb-3 text-center">
                    Завершена: <fmt:formatDate value="${requestScope.game.finished}" pattern="dd.MM.yyyy HH:mm:ss"/>
                </p>
                <c:if test="${sessionScope.isReferee}">
                    <form method="post" class="card-body mb-0 p-0 d-flex justify-content-center">
                        <button type="submit" class="btn btn-outline-danger d-block" onclick="return confirm('Вы уверены, что хотите аннулировать эту партию?')">
                            Аннулировать партию
                        </button>
                    </form>
                </c:if>
            </div>
        </div>
    </div>
</main>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.5/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>