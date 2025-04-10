<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Регистрация</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        .auth-container {
            max-width: 600px;
            margin: 5rem auto;
            padding: 2rem;
            border: 1px solid #dee2e6;
            border-radius: 0.5rem;
        }
        .required:after {
            content: "*";
            color: red;
            margin-left: 3px;
        }
    </style>
</head>
<body class="bg-light">
<div class="container">
    <div class="auth-container bg-white shadow">
        <h2 class="mb-4 text-center">Регистрация</h2>

        <c:if test="${not empty param.error}">
            <div class="alert alert-danger alert-dismissible fade show" role="alert">
                <c:choose>
                    <c:when test="${param.error == 'email_exists'}">
                        Пользователь с таким email уже существует.
                    </c:when>
                    <c:when test="${param.error == 'database_error'}">
                        Ошибка базы данных. Попробуйте позже.
                    </c:when>
                </c:choose>
                <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
            </div>
        </c:if>

        <form action="${pageContext.request.contextPath}/register" method="post">
            <div class="row g-3">
                <div class="col-md-4">
                    <label class="form-label required">Фамилия</label>
                    <input type="text" class="form-control" name="surname" required>
                </div>

                <div class="col-md-4">
                    <label class="form-label required">Имя</label>
                    <input type="text" class="form-control" name="name" required>
                </div>

                <div class="col-md-4">
                    <label class="form-label">Отчество</label>
                    <input type="text" class="form-control" name="patronymic">
                </div>

                <div class="col-12">
                    <label class="form-label required">Дата рождения</label>
                    <input type="date" class="form-control" name="birthday" required>
                </div>

                <div class="col-12">
                    <label class="form-label required">Email</label>
                    <input type="email" class="form-control" name="email" required>
                </div>

                <div class="col-12">
                    <label class="form-label required">Пароль</label>
                    <input type="password" class="form-control" name="password" required>
                </div>

                <div class="col-12">
                    <label class="form-label required">Роль</label>
                    <select class="form-select" name="role" required>
                        <option value="PATIENT">Пациент</option>
                        <option value="DOCTOR">Врач</option>
                    </select>
                </div>

                <div class="col-12">
                    <button type="submit" class="btn btn-primary w-100">Зарегистрироваться</button>
                </div>
            </div>
        </form>

        <div class="text-center mt-4">
            Уже есть аккаунт?
            <a href="${pageContext.request.contextPath}/login" class="link-secondary">Войти</a>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>