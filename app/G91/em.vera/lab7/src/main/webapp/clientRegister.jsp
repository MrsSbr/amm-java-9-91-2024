<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<html>
<head>
    <title>Create Account</title>
    <link href="https://fonts.googleapis.com/css2?family=Poppins&display=swap" rel="stylesheet">
    <style>
        body {
            background-color: #f0f2ff;
            font-family: 'Poppins', sans-serif;
            display: flex;
            align-items: center;
            justify-content: center;
            height: 100vh;
            margin: 0;
        }
        .container {
            background-color: white;
            width: 400px;
            border-radius: 20px;
            box-shadow: 0 8px 20px rgba(0,0,0,0.1);
            overflow: hidden;
        }
        .header {
            padding: 30px;
            background-image: linear-gradient(to right, #8A4DFF, #DD4AFF);
            color: white;
            text-align: center;
        }
        .header h2 {
            margin: 0;
        }
        .header p {
            margin: 5px 0 0 0;
            opacity: 0.9;
            font-size: 14px;
        }
        .form-content {
            padding: 30px;
        }
        .form-group {
            margin-bottom: 20px;
        }
        .form-group input {
            width: 100%;
            padding: 12px;
            border-radius: 10px;
            border: 1px solid #e4e4e4;
            box-sizing: border-box;
            font-size: 14px;
        }
        .form-group input:focus {
            outline: none;
            border-color: #8A4DFF;
        }
        button {
            width: 100%;
            padding: 12px;
            border-radius: 10px;
            border: none;
            color: white;
            background-image: linear-gradient(to right, #8A4DFF, #DD4AFF);
            cursor: pointer;
            font-size: 16px;
            transition: opacity 0.3s ease;
        }
        button:hover {
            opacity: 0.9;
        }
        .footer {
            text-align: center;
            padding-bottom: 20px;
            font-size: 14px;
        }
        .footer a {
            color: #8A4DFF;
            text-decoration: none;
            font-weight: bold;
        }
        .footer a:hover {
            text-decoration: underline;
        }
    </style>
</head>
<body>
<div class="container">
    <div class="header">
        <h2>Create Account</h2>
        <p>Join our community today</p>
    </div>
    <form action="${pageContext.request.contextPath}/register" method="post" class="form-content">
        <div class="form-group">
            <input type="text" name="name" placeholder="Your Name" required>
        </div>
        <div class="form-group">
            <input type="email" name="email" placeholder="Email Address" required>
        </div>
        <div class="form-group">
            <input type="password" name="password" placeholder="Create Password" required>
        </div>
        <button type="submit">Register</button>
    </form>
    <div class="footer">
        Already have an account? <a href="${pageContext.request.contextPath}/login">Sign In</a>
    </div>
</div>
</body>
</html>
