<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Trip Completed</title>
    <style>
        body {
            font-family: "Segoe UI", Tahoma, Geneva, Verdana, sans-serif;
            background-color: #f4f6f8;
            color: #333;
            display: flex;
            flex-direction: column;
            align-items: center;
            justify-content: center;
            height: 100vh;
            margin: 0;
        }

        h1 {
            color: #2e7d32;
            margin-bottom: 20px;
        }

        p {
            font-size: 18px;
            margin-bottom: 30px;
        }

        a {
            text-decoration: none;
            background-color: #1976d2;
            color: white;
            padding: 10px 20px;
            border-radius: 5px;
            transition: background-color 0.2s ease-in-out;
        }

        a:hover {
            background-color: #1565c0;
        }
    </style>
</head>
<body>
<h1>Your Trip is Completed</h1>
<p>Total Price: ${finalPrice} RUB</p>
<a href="/trips">Back to Active Trips</a>
</body>
</html>
