<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Регистрация</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        .error-message {
            color: #dc3545;
            font-size: 0.875em;
            margin-top: 0.25rem;
            display: none;
        }
        .is-invalid {
            border-color: #dc3545;
        }
        .is-invalid:focus {
            border-color: #dc3545;
            box-shadow: 0 0 0 0.25rem rgba(220, 53, 69, 0.25);
        }
        .card {
            border-radius: 10px;
        }
        .card-header {
            border-radius: 10px 10px 0 0 !important;
        }
    </style>
</head>
<body class="bg-light">
<div class="container mt-5">
    <div class="row justify-content-center">
        <div class="col-md-8 col-lg-6">
            <div class="card shadow-sm">
                <div class="card-header bg-primary text-white">
                    <h4 class="text-center mb-0">Регистрация</h4>
                </div>
                <div class="card-body">
                    <form id="registrationForm" action="${pageContext.request.contextPath}/auth/register" method="post" novalidate>
                        <input type="hidden" name="id" value="${user.userId}">

                        <div class="mb-3">
                            <label for="login" class="form-label">Логин</label>
                            <input type="text" class="form-control" id="login" name="login"
                                   value="${user.login}" required>
                            <div class="error-message" id="login-error">Пожалуйста, введите логин</div>
                        </div>

                        <div class="mb-3">
                            <label for="password" class="form-label">Пароль</label>
                            <input type="password" class="form-control" id="password" name="password"
                                   value="${user.password}" required minlength="6">
                            <div class="error-message" id="password-error">Пароль должен содержать минимум 6 символов</div>
                        </div>

                        <div class="mb-3">
                            <label for="username" class="form-label">Имя пользователя</label>
                            <input type="text" class="form-control" id="username" name="username"
                                   value="${user.username}" required>
                            <div class="error-message" id="username-error">Пожалуйста, введите имя пользователя</div>
                        </div>

                        <div class="mb-3">
                            <label for="phoneNumber" class="form-label">Телефон</label>
                            <input type="tel" class="form-control" id="phoneNumber" name="phoneNumber"
                                   value="${user.phoneNumber}" pattern="\+?[0-9]{10,15}" required>
                            <div class="error-message" id="phone-error">
                                Введите корректный номер
                            </div>
                        </div>

                        <div class="mb-3">
                            <label for="email" class="form-label">Email</label>
                            <input type="email" class="form-control" id="email" name="email"
                                   value="${user.email}" pattern="[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,}$">
                            <div class="error-message" id="email-error">
                                Введите корректный email
                            </div>
                        </div>

                        <div class="d-grid gap-2 mt-4">
                            <button type="submit" class="btn btn-primary py-2">Зарегистрироваться</button>
                            <a href="${pageContext.request.contextPath}/auth/login"
                               class="btn btn-outline-secondary py-2">Уже есть аккаунт? Войти</a>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
<script>
    document.addEventListener('DOMContentLoaded', function() {
        const form = document.getElementById('registrationForm');
        const inputs = form.querySelectorAll('input[required]');
        const phoneInput = document.getElementById('phoneNumber');
        const phoneError = document.getElementById('phone-error');
        const emailInput = document.getElementById('email');
        const emailError = document.getElementById('email-error');

        let lastValidPhone = phoneInput.value;

        // Валидация при вводе для всех полей
        inputs.forEach(input => {
            const errorElement = input.nextElementSibling;

            input.addEventListener('input', function() {
                validateField(input, errorElement);
            });

            input.addEventListener('blur', function() {
                validateField(input, errorElement);
            });
        });

        // Специальная обработка для телефона
        phoneInput.addEventListener('input', function(e) {
            // Удаляем все символы, кроме цифр и +
            let cleanedValue = phoneInput.value.replace(/[^\d+]/g, '');

            // Проверяем, начинается ли с +, если нет - добавляем цифры
            if (cleanedValue.startsWith('+')) {
                cleanedValue = '+' + cleanedValue.substring(1).replace(/\D/g, '');
            } else {
                cleanedValue = cleanedValue.replace(/\D/g, '');
            }

            // Сохраняем новое значение
            phoneInput.value = cleanedValue;
            lastValidPhone = cleanedValue;

            // Проверяем валидность
            validatePhone();
        });

        // Валидация email
        emailInput.addEventListener('input', validateEmail);
        emailInput.addEventListener('blur', validateEmail);

        // Обработка отправки формы
        form.addEventListener('submit', function(e) {
            let isValid = true;

            // Проверяем обязательные поля
            inputs.forEach(input => {
                const errorElement = input.nextElementSibling;
                if (!validateField(input, errorElement)) {
                    isValid = false;
                }
            });

            // Проверяем телефон и email
            if (!validatePhone() || !validateEmail()) {
                isValid = false;
            }

            if (!isValid) {
                e.preventDefault();
                // Прокрутка к первому невалидному полю
                const firstInvalid = form.querySelector('.is-invalid');
                if (firstInvalid) {
                    firstInvalid.scrollIntoView({behavior: 'smooth', block: 'center'});
                }
            }
        });

        function validateField(field, errorElement) {
            if (!field.checkValidity()) {
                field.classList.add('is-invalid');
                errorElement.style.display = 'block';
                return false;
            } else {
                field.classList.remove('is-invalid');
                errorElement.style.display = 'none';
                return true;
            }
        }

        function validatePhone() {
            const isValid = phoneInput.checkValidity();

            if (!isValid) {
                phoneInput.classList.add('is-invalid');
                phoneError.style.display = 'block';
                return false;
            } else {
                phoneInput.classList.remove('is-invalid');
                phoneError.style.display = 'none';
                return true;
            }
        }

        function validateEmail() {
            // Email не обязателен, но если введен - проверяем
            if (emailInput.value) {
                const isValid = emailInput.checkValidity();

                if (!isValid) {
                    emailInput.classList.add('is-invalid');
                    emailError.style.display = 'block';
                    return false;
                } else {
                    emailInput.classList.remove('is-invalid');
                    emailError.style.display = 'none';
                    return true;
                }
            }
            return true;
        }
    });
</script>
</body>
</html>
