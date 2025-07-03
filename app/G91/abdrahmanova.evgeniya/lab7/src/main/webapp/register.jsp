<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Регистрация</title>
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
        }

        body {
            display: flex;
            justify-content: center;
            align-items: center;
            min-height: 100vh;
            background: #f8f9fa;
            padding: 20px;
        }

        .register-container {
            background: white;
            border-radius: 12px;
            box-shadow: 0 10px 35px rgba(0, 0, 0, 0.12);
            width: 100%;
            max-width: 520px;
            padding: 40px;
            text-align: center;
            transition: transform 0.3s ease;
        }

        .register-container:hover {
            transform: translateY(-5px);
        }

        .logo {
            margin-bottom: 25px;
        }

        .logo-icon {
            font-size: 48px;
            margin-bottom: 12px;
        }

        h1 {
            color: #333;
            margin-bottom: 6px;
            font-weight: 600;
        }

        .subtitle {
            color: #777;
            margin-bottom: 28px;
            font-size: 16px;
        }

        .input-row {
            display: flex;
            gap: 15px;
            margin-bottom: 18px;
        }

        .input-group {
            flex: 1;
            text-align: left;
            margin-bottom: 0;
        }

        label {
            display: block;
            margin-bottom: 8px;
            color: #555;
            font-weight: 500;
        }

        input, select {
            width: 100%;
            padding: 14px;
            border: 1px solid #e0e0e0;
            border-radius: 8px;
            font-size: 16px;
            transition: all 0.3s;
        }

        input:focus, select:focus {
            outline: none;
            border-color: #aaa;
            box-shadow: 0 0 0 3px rgba(0, 170, 170, 0.1);
        }

        .phone-input {
            display: flex;
        }

        .phone-prefix {
            width: 70px;
            padding: 14px;
            background: #f5f5f5;
            border: 1px solid #e0e0e0;
            border-right: none;
            border-radius: 8px 0 0 8px;
            font-size: 16px;
            text-align: center;
        }

        .phone-number {
            flex: 1;
            border-radius: 0 8px 8px 0;
        }

        .password-hint {
            font-size: 13px;
            color: #999;
            margin-top: 5px;
        }

        .terms {
            display: flex;
            align-items: flex-start;
            margin: 25px 0 20px;
            text-align: left;
        }

        .terms input {
            width: auto;
            margin-right: 12px;
            margin-top: 4px;
        }

        .terms label {
            color: #666;
            font-weight: normal;
            line-height: 1.5;
        }

        .terms a {
            color: inherit;
            text-decoration: underline;
        }

        .btn {
            width: 100%;
            padding: 15px;
            border: none;
            border-radius: 8px;
            font-size: 16px;
            font-weight: 600;
            cursor: pointer;
            transition: all 0.3s;
            margin-top: 8px;
        }

        .btn:hover {
            transform: scale(1.02);
            box-shadow: 0 5px 15px rgba(0, 0, 0, 0.1);
        }

        .btn:active {
            transform: scale(0.98);
        }

        .login-link {
            margin-top: 25px;
            color: #777;
            font-size: 15px;
        }

        .login-link a {
            color: inherit;
            font-weight: 600;
            text-decoration: none;
        }


    </style>
</head>
<body>
<div class="register-container palette-2">
    <div class="logo">
        <div class="logo-icon">📅</div>
        <h1>Создать аккаунт</h1>
    </div>

    <form id="registerForm" action="${pageContext.request.contextPath}/register" method="POST">
        <div class="input-row">
            <div class="input-group">
                <label for="fullname">Полное имя</label>
                <input
                        type="text"
                        id="fullname"
                        name="fullname"
                        placeholder="Иван Иванов Иванович"
                        required>
            </div>

            <div class="input-group">
                <label for="birthdate">Дата рождения</label>
                <input
                        type="date"
                        id="birthdate"
                        name="birthdate"
                        required>
            </div>
        </div>

        <div class="input-row">
            <div class="input-group">
                <label for="email">Email</label>
                <input
                        type="email"
                        id="email"
                        name="email"
                        placeholder="email@example.com"
                        required>
            </div>

            <div class="input-group">
                <label for="phone">Телефон</label>
                <div class="phone-input">
                    <div class="phone-prefix">+7</div>
                    <input
                            type="tel"
                            id="phone"
                            name="phone"
                            class="phone-number"
                            placeholder="900 123-45-67"
                            pattern="[0-9]{3} [0-9]{3}-[0-9]{2}-[0-9]{2}"
                            required>
                </div>
            </div>
        </div>

        <div class="input-row">
            <div class="input-group">
                <label for="password">Пароль</label>
                <input
                        type="password"
                        id="password"
                        name="password"
                        placeholder="••••••••"
                        required>
                <div class="password-hint">Минимум 8 символов, цифры и буквы</div>
            </div>

            <div class="input-group">
                <label for="confirm-password">Подтвердите пароль</label>
                <input
                        type="password"
                        id="confirm-password"
                        name="confirm-password"
                        placeholder="••••••••"
                        required>
            </div>
        </div>

        <div class="input-row">
            <div class="input-group">
                <label for="role">Роль</label>
                <select id="role" name="user_role" class="role-select" required>
                    <c:forEach items="${requestScope.roles}" var="cur_role">
                        <option value="${cur_role}" class="role-option">
                                ${user_role eq cur_role ? 'selected' : ''}
                                ${cur_role}
                                <%--                            <c:choose>--%>
                                <%--                                <c:if test="${role == 'USER'}">--%>
                                <%--                                    <span class="role-icon">👤</span> Пользователь--%>
                                <%--                                </c:if>--%>
                                <%--                                <c:when test="${role == 'GUIDE'}">--%>
                                <%--                                    <span class="role-icon">👨‍🏫</span> Гид--%>
                                <%--                                </c:when>--%>
                                <%--                            </c:choose>--%>
                        </option>
                    </c:forEach>
                </select>
            </div>
        </div>

        <button type="submit" class="btn">Зарегистрироваться</button>
    </form>

    <div class="login-link">
        Уже есть аккаунт? <a href="${pageContext.request.contextPath}/login">Войти</a>
    </div>
</div>

<div class="toast-container"></div>

<script>
    const phoneInput = document.getElementById('phone');
    phoneInput.addEventListener('input', function(e) {
        const x = e.target.value
            .replace(/\D/g, '')
            .match(/(\d{0,3})(\d{0,3})(\d{0,2})(\d{0,2})/);

        if (x) {
            e.target.value =
                (x[1] ? x[1] : '') +
                (x[2] ? ' ' + x[2] : '') +
                (x[3] ? '-' + x[3] : '') +
                (x[4] ? '-' + x[4] : '');
        }
    });

    const today = new Date();
    const minDate = new Date();
    minDate.setFullYear(today.getFullYear() - 100);

    const maxDate = new Date();
    maxDate.setFullYear(today.getFullYear() - 14);

    document.getElementById('birthdate').min = minDate.toISOString().split('T')[0];
    document.getElementById('birthdate').max = maxDate.toISOString().split('T')[0];

    function showToast(message, title = 'Ошибка', type = 'error') {
        const toastContainer = document.querySelector('.toast-container');
        const toast = document.createElement('div');
        toast.className = `toast ${type}-toast`;

        const icons = {
            error: '⚠️',
            success: '✅',
            warning: '⚠️',
            info: 'ℹ️'
        };

        toast.innerHTML = `
                <div class="toast-icon">${icons[type] || '⚠️'}</div>
                <div class="toast-content">
                    <div class="toast-title">${title}</div>
                    <div class="toast-message">${message}</div>
                </div>
                <button class="toast-close">&times;</button>
            `;

        toastContainer.appendChild(toast);

        setTimeout(() => {
            toast.classList.add('show');
        }, 10);

        setTimeout(() => {
            hideToast(toast);
        }, 5000);

        toast.querySelector('.toast-close').addEventListener('click', () => {
            hideToast(toast);
        });
    }

    function hideToast(toast) {
        toast.classList.remove('show');
        setTimeout(() => {
            toast.remove();
        }, 400);
    }

    document.getElementById('registerForm').addEventListener('submit', function(e) {
        e.preventDefault();

        const password = document.getElementById('password').value;
        const confirmPassword = document.getElementById('confirm-password').value;

        if (password !== confirmPassword) {
            showToast('Пароли не совпадают', 'Ошибка ввода', 'error');
            return;
        }

        if (password.length < 8) {
            showToast('Пароль должен содержать минимум 8 символов, включая буквы и цифры', 'Слабый пароль', 'warning');
            return;
        }

        const phone = document.getElementById('phone').value;
        if (!/^\d{3} \d{3}-\d{2}-\d{2}$/.test(phone)) {
            showToast('Введите телефон в формате: 900 123-45-67', 'Некорректный телефон', 'error');
            return;
        }

        const birthdate = document.getElementById('birthdate').value;
        if (!birthdate) {
            showToast('Пожалуйста, укажите дату рождения', 'Не заполнено поле', 'warning');
            return;
        }

        showToast('Регистрация прошла успешно!', 'Успех', 'success');

        this.submit();
    });
</script>
</body>
</html>