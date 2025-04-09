<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Медицинский портал</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        .plan-card {
            transition: transform 0.2s;
            border: 1px solid rgba(0,0,0,.125);
            border-radius: 0.75rem;
            margin-bottom: 1rem;
        }
        .action-buttons {
            margin-bottom: 1.5rem;
        }
    </style>
</head>
<body class="bg-light">
<div class="container py-4">
    <div class="d-flex justify-content-between align-items-center mb-4">
        <h1 class="h3 mb-0 text-primary">
            <i class="bi bi-heart-pulse"></i> Медицинские планы
        </h1>
        <div>
            <a href="${pageContext.request.contextPath}/logout" class="btn btn-danger btn-sm">
                <i class="bi bi-box-arrow-right"></i> Выйти
            </a>
        </div>
    </div>

    <!-- Кнопки действий для доктора -->
    <c:if test="${user.role.name() == 'DOCTOR'}">
        <div class="action-buttons d-flex gap-2 mb-3">
            <a href="${pageContext.request.contextPath}/protected/doctor/patients"
               class="btn btn-outline-primary btn-sm">
                <i class="bi bi-people"></i> Список пациентов
            </a>
            <a href="${pageContext.request.contextPath}/protected/doctor/create-plan"
               class="btn btn-success btn-sm">
                <i class="bi bi-plus-circle"></i> Создать новый план
            </a>
        </div>
    </c:if>

    <c:if test="${user.role.name() == 'DOCTOR'}">
        <div class="row">
            <div class="col-md-6">
                <div class="card shadow-sm">
                    <div class="card-header bg-primary text-white">
                        <h5 class="mb-0">Мои назначения пациентам</h5>
                    </div>
                    <div class="card-body">
                        <c:choose>
                            <c:when test="${not empty doctorPlans}">
                                <c:forEach items="${doctorPlans}" var="plan">
                                    <div class="plan-card p-3">
                                        <h6>${plan.plan.medicationName} (${plan.plan.dosageMg} мг)</h6>
                                        <p class="small mb-1">Пациент: ${plan.patientFullName}</p>
                                        <div class="d-flex justify-content-end gap-2">
                                            <a href="${pageContext.request.contextPath}/protected/doctor/edit-plan?id=${plan.plan.id}"
                                               class="btn btn-sm btn-outline-secondary">
                                                <i class="bi bi-pencil"></i> Редактировать
                                            </a>
                                            <a href="${pageContext.request.contextPath}/protected/view-plan?id=${plan.plan.id}"
                                               class="btn btn-sm btn-outline-primary">
                                                <i class="bi bi-eye"></i> Просмотр
                                            </a>
                                        </div>
                                    </div>
                                </c:forEach>
                            </c:when>
                            <c:otherwise>
                                <div class="text-muted">Нет назначенных планов</div>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>
            </div>

            <div class="col-md-6">
                <div class="card shadow-sm">
                    <div class="card-header bg-info text-white">
                        <h5 class="mb-0">Мои планы лечения</h5>
                    </div>
                    <div class="card-body">
                        <c:choose>
                            <c:when test="${not empty patientPlans}">
                                <c:forEach items="${patientPlans}" var="plan">
                                    <div class="plan-card p-3">
                                        <h6>${plan.plan.medicationName} (${plan.plan.dosageMg} мг)</h6>
                                        <p class="small mb-1">Врач: ${plan.doctorFullName}</p>
                                        <div class="d-flex justify-content-end">
                                            <a href="${pageContext.request.contextPath}/protected/view-plan?id=${plan.plan.id}"
                                               class="btn btn-sm btn-outline-info">
                                                <i class="bi bi-eye"></i> Просмотр
                                            </a>
                                        </div>
                                    </div>
                                </c:forEach>
                            </c:when>
                            <c:otherwise>
                                <div class="text-muted">Нет назначенных планов</div>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>
            </div>
        </div>
    </c:if>

    <c:if test="${user.role.name() != 'DOCTOR'}">
        <div class="card shadow-sm">
            <div class="card-header bg-info text-white">
                <h5 class="mb-0">Мои планы лечения</h5>
            </div>
            <div class="card-body">
                <c:choose>
                    <c:when test="${not empty patientPlans}">
                        <c:forEach items="${patientPlans}" var="plan">
                            <div class="plan-card p-3">
                                <h6>${plan.plan.medicationName} (${plan.plan.dosageMg} мг)</h6>
                                <p class="small mb-1">Врач: ${plan.doctorFullName}</p>
                                <div class="d-flex justify-content-end">
                                    <a href="${pageContext.request.contextPath}/protected/view-plan?id=${plan.plan.id}"
                                       class="btn btn-sm btn-outline-info">
                                        <i class="bi bi-eye"></i> Просмотр
                                    </a>
                                </div>
                            </div>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        <div class="text-muted">План лечения ещё не назначен</div>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </c:if>
</div>

<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>