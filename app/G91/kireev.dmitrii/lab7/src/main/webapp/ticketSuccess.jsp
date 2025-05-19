<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Успешная покупка</title>
    <style>
        body { font-family: Arial, sans-serif; background-color: #f4f4f4; margin: 0; padding: 0; display: flex; justify-content: center; align-items: center; height: 100vh; }
        .success-container { background: white; padding: 30px; border-radius: 5px; box-shadow: 0 0 10px rgba(0,0,0,0.1); text-align: center; width: 400px; }
        h2 { color: #28a745; margin-bottom: 20px; }
        .btn { display: inline-block; padding: 10px 20px; background-color: #007bff; color: white; text-decoration: none; border-radius: 4px; margin-top: 20px; }
        .btn:hover { background-color: #0056b3; }
    </style>
</head>
<body>
<div class="success-container">
    <h2>Покупка билета успешно завершена!</h2>
    <p>Ваш билет был успешно зарезервирован. Детали билета были отправлены на вашу электронную почту.</p>
    <a href="${pageContext.request.contextPath}/" class="btn">Вернуться на главную</a>
</div>
</body>
</html>