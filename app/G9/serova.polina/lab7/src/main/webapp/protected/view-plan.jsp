<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>План лечения</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">
<div class="container py-4">
  <div class="card shadow">
    <div class="card-header bg-primary text-white">
      <h3 class="mb-0">План лечения</h3>
    </div>
    <div class="card-body">
      <dl class="row">
        <dt class="col-sm-3">Препарат</dt>
        <dd class="col-sm-9">${plan.medicationName}</dd>

        <dt class="col-sm-3">Дозировка</dt>
        <dd class="col-sm-9">${plan.dosageMg} мг</dd>

        <dt class="col-sm-3">Длительность</dt>
        <dd class="col-sm-9">${plan.durationDays} дней</dd>

        <dt class="col-sm-3">Врач</dt>
        <dd class="col-sm-9">${doctorName}</dd>

        <dt class="col-sm-3">Пациент</dt>
        <dd class="col-sm-9">${patientName}</dd>

        <dt class="col-sm-3">Время приёма</dt>
        <dd class="col-sm-9">
          <c:forEach items="${plan.takingTime}" var="time">
                            <span class="badge bg-secondary me-1">
                                ${String.format("%02d:%02d", time.hour, time.minute)}
                            </span>
          </c:forEach>
        </dd>
      </dl>
      <div class="mt-4">
        <a href="${pageContext.request.contextPath}/home"
           class="btn btn-outline-secondary">
          Назад
        </a>
        <c:if test="${user.role == 'DOCTOR'}">
          <a href="${pageContext.request.contextPath}/protected/doctor/edit-plan?id=${plan.id}"
             class="btn btn-primary">
            Редактировать
          </a>
        </c:if>
      </div>
    </div>
  </div>
</div>
</body>
</html>