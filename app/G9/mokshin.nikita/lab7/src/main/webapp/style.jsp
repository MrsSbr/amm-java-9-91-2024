<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<style>
    body {
        font-family: Arial, sans-serif;
        display: flex;
        flex-direction: column;
        align-items: center;
        justify-content: center;
        min-height: 100vh;
        margin: 0;
        padding: 20px;
        background-color: #f9f9f9;
    }
    h1 {
        margin-bottom: 20px;
    }
    table {
        width: 60%;
        border-collapse: collapse;
        margin-bottom: 20px;
        background-color: white;
        box-shadow: 0 2px 5px rgba(0,0,0,0.1);
    }
    th, td {
        border: 1px solid #ddd;
        padding: 12px;
        text-align: center;
    }
    th {
        background-color: #f2f2f2;
    }
    input[type="text"] {
        width: 100%;
        padding: 8px;
        box-sizing: border-box;
        border: 1px solid #ccc;
        border-radius: 4px;
    }
    button {
        padding: 10px 20px;
        background-color: #4CAF50;
        color: white;
        border: none;
        border-radius: 5px;
        cursor: pointer;
        transition: background-color 0.3s;
        margin-top: 10px;
    }
    button:hover {
        background-color: #45a049;
    }
    .add-btn {
        background-color: #008CBA;
        margin-bottom: 20px;
    }
    .add-btn:hover {
        background-color: #007bb5;
    }
    .back-btn {
        background-color: #008CBA;
        margin-top: 10px;
    }
    .back-btn:hover {
        background-color: #007bb5;
    }
    a {
        text-decoration: none;
    }
    .error-message {
        background-color: #f8d7da;
        color: #721c24;
        border: 1px solid #f5c6cb;
        padding: 15px;
        border-radius: 5px;
        box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
        font-family: Arial, sans-serif;
        margin-bottom: 20px;
        font-size: 16px;
        display: flex;
        align-items: center;
    }

    .error-message i {
        margin-right: 10px;
    }
</style>
