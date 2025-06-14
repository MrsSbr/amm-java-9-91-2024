<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Список статей</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
            background-color: #f4f4f4;
        }
        .container {
            width: 80%;
            margin: 20px auto;
            background: #fff;
            padding: 20px;
            box-shadow: 0 0 10px rgba(0,0,0,0.1);
        }
        .tab {
            overflow: hidden;
            border: 1px solid #ccc;
            background-color: #f1f1f1;
            border-radius: 5px 5px 0 0;
        }
        .tab button {
            background-color: inherit;
            float: left;
            border: none;
            outline: none;
            cursor: pointer;
            padding: 14px 16px;
            transition: 0.3s;
            font-size: 16px;
        }
        .tab button:hover {
            background-color: #ddd;
        }
        .tab button.active {
            background-color: #4CAF50;
            color: white;
        }
        .tabcontent {
            display: none;
            padding: 20px;
            border: 1px solid #ccc;
            border-top: none;
            animation: fadeEffect 1s;
        }
        @keyframes fadeEffect {
            from {opacity: 0;}
            to {opacity: 1;}
        }
        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
        }
        table, th, td {
            border: 1px solid #ddd;
        }
        th, td {
            padding: 12px;
            text-align: left;
        }
        th {
            background-color: #4CAF50;
            color: white;
        }
        tr:nth-child(even) {
            background-color: #f2f2f2;
        }
        .action-buttons a {
            display: inline-block;
            padding: 5px 10px;
            margin: 2px;
            text-decoration: none;
            color: white;
            border-radius: 3px;
        }
        .edit-btn {
            background-color: #2196F3;
        }
        .delete-btn {
            background-color: #f44336;
        }
        .add-btn {
            background-color: #4CAF50;
            padding: 10px 15px;
            margin-bottom: 20px;
            display: inline-block;
        }
    </style>
</head>
<body>
<div class="container">
    <h1>Список статей</h1>
    <a href="${pageContext.request.contextPath}/articles?action=new" class="add-btn">Добавить новую статью</a>

    <table>
        <thead>
        <tr>
            <th>ID</th>
            <th>Заголовок</th>
            <th>Дата публикации</th>
            <th>Категория</th>
            <th>Автор</th>
            <th>Действия</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="article" items="${articles}">
            <tr>
                <td>${article.id}</td>
                <td>${article.title}</td>
                <td>${article.datePublication}</td>
                <td>${article.category.name}</td>
                <td>${article.author.surname} ${article.author.name}</td>
                <td class="action-buttons">
                    <a href="${pageContext.request.contextPath}/articles?action=edit&id=${article.id}" class="edit-btn">Редактировать</a>
                    <a href="${pageContext.request.contextPath}/articles?action=delete&id=${article.id}" class="delete-btn">Удалить</a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
</body>
</html>