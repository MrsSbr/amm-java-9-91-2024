<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Турбаза</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 20px;
            display: flex;
            flex-direction: column;
            align-items: center;
        }
        .home-container {
            margin-top: 50px;
            border: 1px solid #4682b4;
            border-radius: 5px;
            padding: 20px;
            width: 300px;
            text-align: center;
        }
        .btn {
            display: block;
            width: 100%;
            padding: 10px;
            margin: 10px 0;
            font-size: 16px;
            color: white;
            background-color: #4682b4;
            border: none;
            border-radius: 3px;
            text-align: center;
            text-decoration: none;
            cursor: pointer;
            box-sizing: border-box;
        }
        .btn:hover {
            background-color: #3a6d99;
        }
        .logout-btn {
            background-color: #f44336;
        }
        .logout-btn:hover {
            background-color: #d32f2f;
        }
        h1 {
            color: #4682b4;
        }
    </style>

</head>
<body>
<div class="home-container">
    <h1>Добро пожаловать!</h1>

    <form action="rent" method="get">
        <input type="submit" class="btn" value="Забронировать">
    </form>

    <form action="myrent" method="get">
        <input type="submit" class="btn" value="Мои брони">
    </form>

    <form action="logout" method="get">
        <input type="submit" class="btn logout-btn" value="Выйти">
    </form>
</div>
</body>
</html>