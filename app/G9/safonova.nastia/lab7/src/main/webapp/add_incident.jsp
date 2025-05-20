<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<html>
<head>
    <title>Новый инцидент</title>
    <style>
        body { background-color: #f5f5dc; font-family: Arial; }
        .container { max-width: 600px; margin: 20px auto; padding: 20px; }
        .form-group { margin-bottom: 15px; }
        label { display: block; margin-bottom: 5px; color: #556b2f; }
        input, select, textarea {
            width: 100%;
            padding: 8px;
            border: 1px solid #ccc;
            border-radius: 4px;
            box-sizing: border-box;
        }
        .btn {
            background: #8fbc8f;
            color: white;
            padding: 10px 20px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
        }
        .btn-cancel {
            background: #d2b48c;
            margin-left: 10px;
        }
    </style>
</head>
<body>
<div class="container">
    <h1>Добавление инцидента</h1>

    <c:if test="${not empty error}">
        <div style="color: red; margin-bottom: 15px;">${error}</div>
    </c:if>

    <form action="add_incident" method="post">
        <div class="form-group">
            <label>Сотрудник:</label>
            <select name="emplId" required>
                <c:forEach items="${employees}" var="employee">
                    <option value="${employee.idEmpl}">
                            ${employee.surnameEmpl} ${employee.nameEmpl}
                    </option>
                </c:forEach>
            </select>
        </div>

        <div class="form-group">
            <label>Динозавр:</label>
            <select name="dinoId" required>
                <c:forEach items="${dinos}" var="dino">
                    <option value="${dino.idDino}">${dino.nameDino}</option>
                </c:forEach>
            </select>
        </div>

        <div class="form-group">
            <label>Дата инцидента:</label>
            <input type="date" name="date" required>
        </div>

        <div class="form-group">
            <label>Описание:</label>
            <textarea name="description" rows="4" required></textarea>
        </div>

        <button type="submit" class="btn">Создать инцидент</button>
        <button type="button" onclick="location.href='mainPage.jsp'" class="btn btn-cancel">Отмена</button>
    </form>
</div>
</body>
</html>