<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Система управления контентом</title>
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
        .content-container {
            text-align: center;
            margin-top: 30px;
        }
        .content-link {
            display: inline-block;
            padding: 15px 30px;
            background-color: #4CAF50;
            color: white;
            text-decoration: none;
            border-radius: 5px;
            font-size: 18px;
            transition: all 0.3s ease;
            margin: 10px;
        }
        .content-link:hover {
            background-color: #45a049;
            transform: translateY(-3px);
            box-shadow: 0 5px 15px rgba(0,0,0,0.1);
        }
        .add-btn {
            display: inline-block;
            padding: 10px 15px;
            background-color: #2196F3;
            color: white;
            text-decoration: none;
            border-radius: 4px;
            margin: 10px;
        }
        .add-btn:hover {
            background-color: #0b7dda;
        }
    </style>
</head>
<body>
<div class="container">
    <h1>Система управления контентом</h1>

    <div class="tab">
        <button class="tablinks active" onclick="openTab(event, 'articles')">Статьи</button>
        <button class="tablinks" onclick="openTab(event, 'authors')">Авторы</button>
        <button class="tablinks" onclick="openTab(event, 'categories')">Категории</button>
    </div>

    <!-- Вкладка статей -->
    <div id="articles" class="tabcontent" style="display: block;">
        <h2>Система управления статьями</h2>
        <div class="content-container">
            <a href="${pageContext.request.contextPath}/articles" class="content-link">
                Перейти к списку статей
            </a>
            <a href="${pageContext.request.contextPath}/articles?action=new" class="add-btn">
                Добавить новую статью
            </a>
        </div>
    </div>

    <!-- Вкладка авторов -->
    <div id="authors" class="tabcontent">
        <h2>Система управления авторами</h2>
        <div class="content-container">
            <a href="${pageContext.request.contextPath}/authors" class="content-link">
                Перейти к списку авторов
            </a>
            <a href="${pageContext.request.contextPath}/authors?action=new" class="add-btn">
                Добавить нового автора
            </a>
        </div>
    </div>

    <!-- Вкладка категорий -->
    <div id="categories" class="tabcontent">
        <h2>Система управления категориями</h2>
        <div class="content-container">
            <a href="${pageContext.request.contextPath}/categories" class="content-link">
                Перейти к списку категорий
            </a>
            <a href="${pageContext.request.contextPath}/categories?action=new" class="add-btn">
                Добавить новую категорию
            </a>
        </div>
    </div>
</div>

<script>
    function openTab(evt, tabName) {
        var i, tabcontent, tablinks;

        tabcontent = document.getElementsByClassName("tabcontent");
        for (i = 0; i < tabcontent.length; i++) {
            tabcontent[i].style.display = "none";
        }

        tablinks = document.getElementsByClassName("tablinks");
        for (i = 0; i < tablinks.length; i++) {
            tablinks[i].className = tablinks[i].className.replace(" active", "");
        }

        document.getElementById(tabName).style.display = "block";
        evt.currentTarget.className += " active";
    }
</script>
</body>
</html>