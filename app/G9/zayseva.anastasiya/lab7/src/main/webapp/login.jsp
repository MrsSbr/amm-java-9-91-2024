<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Вход в систему</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        .card {
            box-shadow: 0 4px 8px rgba(0,0,0,0.1);
        }
        .alert {
            margin-top: 1rem;
        }
    </style>
</head>
<body>
<div class="container mt-5">
    <div class="row justify-content-center">
        <div class="col-md-6 col-lg-4">
            <div class="card">
                <div class="card-header bg-primary text-white">
                    <h4 class="text-center mb-0">Вход в систему</h4>
                </div>
                <div class="card-body">
                    <form action="${pageContext.request.contextPath}/auth/login" method="post">
                        <input type="hidden" name="loginAttempt" value="true">

                        <div class="mb-3">
                            <label for="login" class="form-label">Логин</label>
                            <input type="text" class="form-control" id="login" name="login"
                                   value="${param.login}" required>
                        </div>

                        <div class="mb-3">
                            <label for="password" class="form-label">Пароль</label>
                            <input type="password" class="form-control" id="password"
                                   name="password" required>
                        </div>

                        <c:if test="${not empty error and not empty param.loginAttempt}">
                            <div class="alert alert-danger alert-dismissible fade show">
                                    ${error}
                                <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
                            </div>
                        </c:if>

                        <div class="d-grid gap-2 mb-3">
                            <button type="submit" class="btn btn-primary">Войти</button>
                        </div>

                        <div class="text-center">
                            <a href="${pageContext.request.contextPath}/auth/register" class="text-decoration-none">
                                Нет аккаунта? Зарегистрироваться
                            </a>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>