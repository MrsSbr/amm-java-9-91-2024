<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Создание плана лечения</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        .time-input-group {
            display: grid;
            grid-template-columns: repeat(auto-fill, minmax(120px, 1fr));
            gap: 0.5rem;
        }
    </style>
</head>
<body class="bg-light">
<div class="container py-4">
    <div class="d-flex justify-content-between align-items-center mb-4">
        <h2>Создание нового плана лечения</h2>
        <a href="${pageContext.request.contextPath}/protected/home"
           class="btn btn-outline-secondary">
            ← Отмена
        </a>
    </div>

    <c:if test="${not empty error}">
        <div class="alert alert-danger">${error}</div>
    </c:if>

    <form method="post" class="card shadow">
        <div class="card-body">
            <div class="mb-3">
                <label class="form-label">Пациент:</label>
                <c:choose>
                    <c:when test="${not empty param.patientId}">
                        <input type="hidden" name="patientId" value="${param.patientId}">
                        <select class="form-select" disabled>
                            <c:forEach items="${patients}" var="patient">
                                <option value="${patient.id}"
                                    ${patient.id == param.patientId ? 'selected' : ''}>
                                        ${patient.surname} ${patient.name}
                                    <c:if test="${not empty patient.patronymic}">
                                        ${patient.patronymic}
                                    </c:if>
                                </option>
                            </c:forEach>
                        </select>
                    </c:when>
                    <c:otherwise>
                        <select name="patientId" class="form-select" required>
                            <option value="">Выберите пациента</option>
                            <c:forEach items="${patients}" var="patient">
                                <option value="${patient.id}">
                                        ${patient.surname} ${patient.name}
                                    <c:if test="${not empty patient.patronymic}">
                                        ${patient.patronymic}
                                    </c:if>
                                </option>
                            </c:forEach>
                        </select>
                    </c:otherwise>
                </c:choose>
            </div>

            <div class="mb-3">
                <label class="form-label">Название препарата:</label>
                <input type="text" name="medicationName"
                       class="form-control" required>
            </div>

            <div class="row g-3 mb-3">
                <div class="col-md-6">
                    <label class="form-label">Дозировка (мг):</label>
                    <input type="number" step="0.1" name="dosageMg"
                           class="form-control"
                           min="0.01"
                           required
                           oninvalid="this.setCustomValidity('Дозировка должна быть не менее 0.01 мг')"
                           oninput="this.setCustomValidity('')">
                </div>
                <div class="col-md-6">
                    <label class="form-label">Длительность (дней):</label>
                    <input type="number" name="durationDays"
                           class="form-control"
                           min="1"
                           required
                           oninvalid="this.setCustomValidity('Длительность должна быть не менее 1 дня')"
                           oninput="this.setCustomValidity('')">
                </div>
            </div>

            <div class="mb-4">
                <label class="form-label">Время приема:</label>
                <div class="time-input-group">
                    <input type="time" name="takingTime"
                           class="form-control" required>
                    <input type="time" name="takingTime"
                           class="form-control">
                    <input type="time" name="takingTime"
                           class="form-control">
                    <input type="time" name="takingTime"
                           class="form-control">
                </div>
                <div class="form-text">Укажите минимум одно время приема</div>
            </div>

            <button type="submit" class="btn btn-primary w-100">
                Сохранить план
            </button>
        </div>
    </form>
</div>
</body>
</html>