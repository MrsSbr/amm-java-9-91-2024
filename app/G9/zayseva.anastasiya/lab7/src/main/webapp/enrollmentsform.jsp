<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>${empty enrollment ? 'Добавить запись' : 'Редактировать запись'}</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container mt-4">
    <h2>${empty enrollment ? 'Добавить запись на курс' : 'Редактировать запись на курс'}</h2>

    <form action="${pageContext.request.contextPath}/enrollments/save" method="post">
        <input type="hidden" name="id" value="${enrollment.userCourseId}">

        <div class="mb-3">
            <label for="userId" class="form-label">Пользователь</label>
            <select class="form-select" id="userId" name="userId" required>
                <option value="">Выберите пользователя</option>
                <c:forEach var="user" items="${users}">
                    <option value="${user.userId}" ${enrollment.userId == user.userId ? 'selected' : ''}>${user.username}</option>
                </c:forEach>
            </select>
        </div>

        <div class="mb-3">
            <label for="courseId" class="form-label">Курс</label>
            <select class="form-select" id="courseId" name="courseId" required>
                <option value="">Выберите курс</option>
                <c:forEach var="course" items="${courses}">
                    <option value="${course.courseId}" ${enrollment.courseId == course.courseId ? 'selected' : ''}>${course.title}</option>
                </c:forEach>
            </select>
        </div>

        <div class="mb-3">
            <label for="status" class="form-label">Статус</label>
            <select class="form-select" id="status" name="status" required>
                <option value="ACTIVE" ${enrollment.enrollmentStatus == 'ACTIVE' ? 'selected' : ''}>Активный</option>
                <option value="COMPLETED" ${enrollment.enrollmentStatus == 'COMPLETED' ? 'selected' : ''}>Завершен</option>
                <option value="CANCELLED" ${enrollment.enrollmentStatus == 'CANCELLED' ? 'selected' : ''}>Отменен</option>
                <option value="PAUSED" ${enrollment.enrollmentStatus == 'PAUSED' ? 'selected' : ''}>Приостановлен</option>
            </select>
        </div>

        <button type="submit" class="btn btn-primary">Сохранить</button>
        <a href="${pageContext.request.contextPath}/enrollments/list" class="btn btn-secondary">Отмена</a>
    </form>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>