<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Главная страница</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container">
    <h1>Добро пожаловать в систему образования</h1>
    <div class="row mt-4">
        <div class="col-md-6">
            <div class="card">
                <div class="card-header">
                    <h5>Курсы</h5>
                </div>
                <div class="card-body">
                    <p>Всего курсов: ${courses.size()}</p>
                    <a href="${pageContext.request.contextPath}/courses/list" class="btn btn-primary">Все курсы</a>
                </div>
            </div>
        </div>
        <div class="col-md-6">
            <div class="card">
                <div class="card-header">
                    <h5>Пользователи</h5>
                </div>
                <div class="card-body">
                    <p>Всего пользователей: ${usersCount}</p>
                    <a href="${pageContext.request.contextPath}/users/list" class="btn btn-primary">Все пользователи</a>
                </div>
            </div>
        </div>
    </div>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>