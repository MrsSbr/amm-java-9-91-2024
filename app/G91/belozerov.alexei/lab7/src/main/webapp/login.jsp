<%@ page language="java"
         pageEncoding="UTF-8"
         contentType="text/html; charset=UTF-8"
         import="ru.vsu.amm.java.utils.ApplicationConstants" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="ru">
<head>
  <meta charset="UTF-8">
  <title>Вход</title>

  <!-- Bootstrap 5 -->
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
        rel="stylesheet">
</head>
<body class="bg-light">

<div class="container py-5">
  <div class="row justify-content-center">
    <div class="col-md-6 col-lg-5">
      <div class="card shadow-sm">
        <div class="card-body p-4">
          <h2 class="card-title mb-4 text-center">Вход</h2>

          <!-- Ошибка от сервлета -->
          <c:if test="${not empty errorMessage}">
            <div class="alert alert-danger">${errorMessage}</div>
          </c:if>

          <form action="${pageContext.request.contextPath}<%= ApplicationConstants.LOGIN_URL %>"
                method="post"
                class="needs-validation" novalidate>

            <div class="mb-3">
              <label for="email" class="form-label">Email</label>
              <input id="email" name="email" type="email"
                     class="form-control" required>
              <div class="invalid-feedback">Введите корректный email.</div>
            </div>

            <div class="mb-4">
              <label for="password" class="form-label">Пароль</label>
              <input id="password" name="password" type="password"
                     class="form-control" required>
              <div class="invalid-feedback">Пароль обязателен.</div>
            </div>

            <button class="btn btn-primary w-100" type="submit">Войти</button>
          </form>

          <!-- Кнопка регистрации -->
          <div class="text-center mt-3">
            <a class="btn btn-outline-secondary w-100"
               href="${pageContext.request.contextPath}<%= ApplicationConstants.REGISTER_URL %>">
              Регистрация
            </a>
          </div>

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
