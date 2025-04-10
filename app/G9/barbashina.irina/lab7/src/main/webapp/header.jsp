<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>${param.title}</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <header>
        <h1>Система управления статьями</h1>
        <nav>
            <a href="articles">Главная</a>
            <a href="articles?action=new">Добавить статью</a>
        </nav>
    </header>
    <main>