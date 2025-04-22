<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>Редактирование плана лечения</title>
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
    <h2>Редактирование плана лечения</h2>
    <a href="${pageContext.request.contextPath}/protected/view-plan?id=${plan.id}"
       class="btn btn-outline-secondary">
      ← Отмена
    </a>
  </div>

  <c:if test="${not empty error}">
    <div class="alert alert-danger">${error}</div>
  </c:if>

  <form method="post" class="card shadow">
    <div class="card-body">
      <input type="hidden" name="id" value="${plan.id}">

      <div class="mb-3">
        <label class="form-label">Пациент:</label>
        <input type="text" class="form-control"
               value="${patientName}" disabled>
      </div>

      <div class="mb-3">
        <label class="form-label">Название препарата:</label>
        <input type="text" name="medicationName"
               class="form-control"
               value="${plan.medicationName}" required>
      </div>

      <div class="row g-3 mb-3">
        <div class="col-md-6">
          <label class="form-label">Дозировка (мг):</label>
          <input type="number" step="0.1" name="dosageMg"
                 class="form-control"
                 value="${plan.dosageMg}"
                 min="0.01"
                 required
                 oninvalid="this.setCustomValidity('Дозировка должна быть не менее 0.01 мг')"
                 oninput="this.setCustomValidity('')">
        </div>
        <div class="col-md-6">
          <label class="form-label">Длительность (дней):</label>
          <input type="number" name="durationDays"
                 class="form-control"
                 value="${plan.durationDays}"
                 min="1"
                 required
                 oninvalid="this.setCustomValidity('Длительность должна быть не менее 1 дня')"
                 oninput="this.setCustomValidity('')">
        </div>
      </div>

      <div class="mb-4">
        <label class="form-label">Время приема:</label>
        <div class="time-input-group">
          <c:forEach items="${plan.takingTime}" var="time" varStatus="status">
            <input type="time" name="takingTime"
                   value="${String.format("%02d:%02d", time.hour, time.minute)}"
                   class="form-control"
              ${status.first ? 'required' : ''}>
          </c:forEach>
          <c:forEach begin="${plan.takingTime.size()}" end="3">
            <input type="time" name="takingTime"
                   class="form-control">
          </c:forEach>
        </div>
        <div class="form-text">Укажите минимум одно время приема</div>
      </div>

      <div class="d-flex justify-content-between">
        <button type="submit" class="btn btn-primary">
          Сохранить изменения
        </button>
        <button type="submit" formaction="${pageContext.request.contextPath}/protected/doctor/delete-plan"
                class="btn btn-danger"
                onclick="return confirm('Вы уверены, что хотите удалить этот план лечения?')">
          Удалить план
        </button>
      </div>
    </div>
  </form>
</div>
</body>
</html>