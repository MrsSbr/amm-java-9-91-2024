<%@ page language="java"
         pageEncoding="UTF-8"
         contentType="text/html; charset=UTF-8"
         import="ru.vsu.amm.java.utils.ApplicationConstants" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Регистрация</title>

    <!-- Bootstrap 5 -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
          rel="stylesheet">
</head>
<body class="bg-light">

<div class="container py-5">
    <div class="row justify-content-center">
        <div class="col-md-7 col-lg-6">
            <div class="card shadow-sm">
                <div class="card-body p-4">
                    <h2 class="card-title mb-4 text-center">Регистрация</h2>

                    <!-- Ошибка от сервлета -->
                    <c:if test="${not empty errorMessage}">
                        <div class="alert alert-danger">${errorMessage}</div>
                    </c:if>

                    <form action="${pageContext.request.contextPath}<%= ApplicationConstants.REGISTER_URL %>"
                          method="post"
                          class="needs-validation" novalidate>

                        <div class="mb-3">
                            <label for="name" class="form-label">Имя (как обращаться)</label>
                            <input id="name" name="name" type="text"
                                   class="form-control" required minlength="2">
                            <div class="invalid-feedback">Введите имя (минимум 2 символа).</div>
                        </div>

                        <div class="mb-3">
                            <label for="email" class="form-label">Email</label>
                            <input id="email" name="email" type="email"
                                   class="form-control" required>
                            <div class="invalid-feedback">Введите корректный email.</div>
                        </div>

                        <div class="mb-4">
                            <label for="password" class="form-label">Пароль</label>
                            <input id="password" name="password" type="password"
                                   class="form-control" required minlength="6">
                            <div class="invalid-feedback">Минимум 6 символов.</div>
                        </div>

                        <button class="btn btn-success w-100" type="submit">Создать аккаунт</button>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>

<!-- Bootstrap JS + валидация -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
<script>
    (() => {
        'use strict';
        const forms = document.querySelectorAll('.needs-validation');
        Array.from(forms).forEach(f => {
            f.addEventListener('submit', e => {
                if (!f.checkValidity()) {
                    e.preventDefault();
                    e.stopPropagation();
                }
                f.classList.add('was-validated');
            }, false);
        });
    })();
</script>
</body>
</html>
