<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Вход в систему</title>
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
            background: #f0f2f5;
            padding: 20px;
        }

        .login-container {
            background: white;
            border-radius: 12px;
            box-shadow: 0 8px 30px rgba(0, 0, 0, 0.1);
            width: 100%;
            max-width: 420px;
            padding: 40px;
            text-align: center;
            transition: transform 0.3s ease;
        }

        .login-container:hover {
            transform: translateY(-5px);
        }

        .logo {
            margin-bottom: 30px;
        }

        .logo-icon {
            font-size: 48px;
            margin-bottom: 10px;
        }

        h1 {
            color: #333;
            margin-bottom: 8px;
            font-weight: 600;
        }

        .subtitle {
            color: #777;
            margin-bottom: 30px;
            font-size: 16px;
        }

        .input-group {
            margin-bottom: 20px;
            text-align: left;
        }

        label {
            display: block;
            margin-bottom: 8px;
            color: #555;
            font-weight: 500;
        }

        input {
            width: 100%;
            padding: 14px;
            border: 1px solid #ddd;
            border-radius: 8px;
            font-size: 16px;
            transition: border 0.3s;
        }

        input:focus {
            outline: none;
            border-color: #aaa;
        }

        .btn {
            width: 100%;
            padding: 14px;
            border: none;
            border-radius: 8px;
            font-size: 16px;
            font-weight: 600;
            cursor: pointer;
            transition: background 0.3s, transform 0.2s;
            margin-top: 10px;
        }

        .btn:hover {
            transform: scale(1.02);
        }

        .btn:active {
            transform: scale(0.98);
        }

        .links {
            margin-top: 25px;
            display: flex;
            justify-content: space-between;
        }

        a {
            color: #777;
            text-decoration: none;
            font-size: 14px;
            transition: color 0.3s;
        }

        a:hover {
            text-decoration: underline;
        }

        .toast-container {
            position: fixed;
            bottom: 30px;
            right: 30px;
            z-index: 1000;
            display: flex;
            flex-direction: column;
            gap: 15px;
        }

        .toast {
            padding: 18px 25px;
            border-radius: 10px;
            box-shadow: 0 6px 20px rgba(0, 0, 0, 0.15);
            display: flex;
            align-items: center;
            max-width: 350px;
            transform: translateX(110%);
            transition: transform 0.4s cubic-bezier(0.34, 1.56, 0.64, 1);
        }

        .toast.show {
            transform: translateX(0);
        }

        .toast-icon {
            font-size: 24px;
            margin-right: 15px;
        }

        .toast-content {
            flex: 1;
        }

        .toast-title {
            font-weight: 600;
            margin-bottom: 4px;
        }

        .toast-message {
            font-size: 14px;
            color: #555;
        }

        .toast-close {
            background: none;
            border: none;
            font-size: 20px;
            cursor: pointer;
            color: #999;
            margin-left: 15px;
            transition: color 0.2s;
        }

        .toast-close:hover {
            color: #333;
        }

        .palette-1 .logo-icon { color: #0aa; }
        .palette-1 .btn { background: #0aa; color: white; }
        .palette-1 .btn:hover { background: #099; }
        .palette-1 input:focus, .palette-1 select:focus { box-shadow: 0 0 0 3px rgba(0, 170, 170, 0.15); }
        .palette-1 .terms a:hover, .palette-1 .login-link a:hover { color: #0aa; }
        .palette-1 .toast { background: #f0ffff; border-left: 5px solid #0aa; }
        .palette-1 .toast-icon { color: #0aa; }

        .palette-2 .logo-icon { color: #ff7043; }
        .palette-2 .btn { background: #ff7043; color: white; }
        .palette-2 .btn:hover { background: #ff5722; }
        .palette-2 input:focus, .palette-2 select:focus { box-shadow: 0 0 0 3px rgba(255, 112, 67, 0.15); }
        .palette-2 .terms a:hover, .palette-2 .login-link a:hover { color: #ff7043; }
        .palette-2 .toast { background: #fff0eb; border-left: 5px solid #ff7043; }
        .palette-2 .toast-icon { color: #ff7043; }
    </style>
</head>
<body>

<div class="login-container palette-1">
    <div class="logo">
        <div class="logo-icon">🔒</div>
        <h1>Добро пожаловать</h1>
        <p class="subtitle">Войдите в свой аккаунт</p>
    </div>

    <form action="${pageContext.request.contextPath}/login" method="POST">
        <div class="input-group">
            <label for="email">Почта</label>
            <input
                    type="text"
                    id="email"
                    name="email"
                    placeholder="Введите ваш email"
                    required>
        </div>

        <div class="input-group">
            <label for="password">Пароль</label>
            <input
                    type="password"
                    id="password"
                    name="password"
                    placeholder="••••••••"
                    required>
        </div>

        <button type="submit" class="btn">Войти</button>
    </form>

    <div class="links">
        <a href="${pageContext.request.contextPath}/register">Регистрация</a>
    </div>
</div>

<div class="toast-container"></div>

<script>
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
</script>
</body>
</html>