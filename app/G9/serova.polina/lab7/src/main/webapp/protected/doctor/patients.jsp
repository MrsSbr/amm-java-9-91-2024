<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Список пациентов</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">
    <div class="container py-4">
        <div class="d-flex justify-content-between align-items-center mb-4">
            <h2>Список пациентов</h2>
            <a href="${pageContext.request.contextPath}/protected/home"
               class="btn btn-outline-primary">
                ← Назад
            </a>
        </div>

        <div class="card shadow">
            <div class="card-body">
                <table class="table table-hover">
                    <thead>
                    <tr>
                        <th>ФИО</th>
                        <th>Дата рождения</th>
                        <th>Email</th>
                        <th>Действия</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${patients}" var="patient">
                        <tr>
                            <td>
                                    ${patient.surname} ${patient.name}
                                <c:if test="${not empty patient.patronymic}">
                                    ${patient.patronymic}
                                </c:if>
                            </td>
                            <td>${patient.birthday}</td>
                            <td>${patient.email}</td>
                            <td>
                                <a href="${pageContext.request.contextPath}/protected/doctor/create-plan?patientId=${patient.id}"
                                   class="btn btn-sm btn-success">
                                    Создать план
                                </a>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
    </body>
</html>