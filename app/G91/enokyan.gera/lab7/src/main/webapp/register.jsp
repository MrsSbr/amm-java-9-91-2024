<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!doctype html>
<html lang="en" class="h-100">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Регистрация</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.5/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        .form-register {
            max-width: 350px;
        }
        .form-register .form-floating:focus-within {
            z-index: 2;
        }
        .form-register input[type="text"] {
            margin-bottom: -1px;
        }
    </style>
</head>
<body class="d-flex align-items-center py-4 bg-body-tertiary h-100">
<main class="form-register w-100 m-auto p-3">
    <form action="${pageContext.request.contextPath}/auth/register" method="post">
        <h1 class="h3 mb-3 fw-normal text-center">Регистрация</h1>
        <c:if test="${not empty param.error}">
            <div class="alert alert-danger alert-dismissible fade show mb-2" role="alert">
                <c:choose>
                    <c:when test="${param.error == 'nicknameIsAlreadyTaken'}">
                        Никнейм уже занят!
                    </c:when>
                    <c:otherwise>
                        Неизвестная ошибка!
                    </c:otherwise>
                </c:choose>
                <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
            </div>
        </c:if>
        <div class="form-floating">
            <input type="text" class="form-control rounded-0 rounded-top" id="floatingInput" name="nickname" placeholder="Example" required>
            <label for="floatingInput">Никнейм</label>
        </div>
        <div class="form-floating">
            <input type="password" class="form-control rounded-0 rounded-bottom mb-2" id="floatingPassword" name="password" placeholder="Password" required>
            <label for="floatingPassword">Пароль</label>
        </div>
        <button class="btn btn-primary w-100 py-2 mb-1" type="submit">Зарегистрироваться</button>
        <div class="text-center">
            <a href="${pageContext.request.contextPath}/auth/login" class="link-secondary">Войти</a>
        </div>
    </form>
</main>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.5/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>