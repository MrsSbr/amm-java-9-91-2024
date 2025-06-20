<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Создать доску</title>
    <meta charset="UTF-8">
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
            background-color: #f5f5f5;
        }
        .header {
            background-color: #0079bf;
            color: white;
            padding: 16px;
            display: flex;
            justify-content: space-between;
            align-items: center;
            box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
        }
        .logo {
            font-size: 24px;
            font-weight: bold;
        }
        .boards-link {
            color: white;
            text-decoration: none;
            background: #ffffff33;
            padding: 10px 16px;
            border-radius: 4px;
        }
        .boards-link:hover {
            background-color: #CDDBE233;
        }
        .boards-link-container {
            display: flex;
            align-items: center;
        }
        .container {
            max-width: 800px;
            margin: 32px auto;
            padding: 0 16px;
        }
        .form-container {
            background: white;
            padding: 32px;
            border-radius: 8px;
            box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
        }
        .form-title {
            font-size: 18pt;
            color: #172b4d;
            margin-bottom: 24px;
        }
        .form-group {
            margin-bottom: 24px;
        }
        label {
            display: block;
            margin-bottom: 8px;
            color: #5e6c84;
            font-weight: bold;
        }
        input[type="text"],
        textarea {
            width: 100%;
            padding: 12px;
            border: 1px solid #dfe1e6;
            border-radius: 4px;
            box-sizing: border-box;
            font-family: Arial, sans-serif;
        }
        textarea {
            min-height: 100px;
            resize: vertical;
        }
        .btn {
            padding: 12px 24px;
            background-color: #0079bf;
            color: white;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            font-size: 12pt;
            transition: background-color 0.2s;
        }
        .btn:hover {
            background-color: #026aa7;
        }
        .btn-cancel {
            background-color: #dfe1e6;
            color: #172b4d;
            margin-left: 16px;
        }
        .btn-cancel:hover {
            background-color: #c1c7d0;
        }
        .error-message {
            color: #dc3545;
            margin-top: 16px;
        }
    </style>
</head>
<body>
    <div class="header">
        <div class="logo">Planner App</div>
        <div class="boards-link-container">
            <a href="${pageContext.request.contextPath}/boards" class="boards-link">Назад к доскам</a>
        </div>
    </div>

    <div class="container">
        <div class="form-container">
            <h1 class="form-title">Создать новую доску</h1>

            <c:if test="${not empty error}">
                <div class="error-message">${error}</div>
            </c:if>

            <form action="${pageContext.request.contextPath}/boards/create" method="post">
                <div class="form-group">
                    <label for="title">Название доски *</label>
                    <input type="text" id="title" name="title" required>
                </div>
                <div class="form-group">
                    <label for="description">Описание (необязательно)</label>
                    <textarea id="description" name="description"></textarea>
                </div>
                <div>
                    <button type="submit" class="btn">Создать доску</button>
                    <button type="button" onclick="window.location.href='/boards'"
                        class="btn btn-cancel">Отмена</button>
                </div>
            </form>
        </div>
    </div>
</body>
</html>