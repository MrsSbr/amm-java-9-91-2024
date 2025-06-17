<style>
    body {
        font-family: 'Arial', sans-serif;
        background-color: #f9e6f9;
        display: flex;
        justify-content: center;
        align-items: center;
        flex-direction: column;
        min-height: 100vh;
        margin: 0;
    }

    .registration-container {
        background-color: #ffffff;
        padding: 30px;
        border-radius: 15px;
        box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
        width: 300px;
        text-align: center;
    }

    h2 {
        color: #ff66b3;
        margin-bottom: 20px;
    }

    input[type="text"], input[type="password"] {
        width: 100%;
        padding: 10px;
        margin: 10px 0;
        border: 2px solid #ff66b3;
        border-radius: 5px;
        font-size: 16px;
        color: #333;
    }

    input[type="submit"] {
        background-color: #ff66b3;
        color: white;
        padding: 10px 20px;
        border: none;
        border-radius: 5px;
        font-size: 16px;
        cursor: pointer;
        transition: background-color 0.3s ease;
    }

    input[type="submit"]:hover {
        background-color: #ff4da1;
    }

    a {
        color: #ff66b3;
        text-decoration: none;
        font-weight: bold;
    }

    a:hover {
        text-decoration: underline;
    }

    .error-message {
        color: #ff1a1a;
        margin-top: 15px;
        font-size: 14px;
    }

    .summary {
        background-color: #fff0f5;
        padding: 20px;
        border-radius: 15px;
        box-shadow: 0 4px 8px rgba(0,0,0,0.1);
        margin-bottom: 30px;
        width: 500px;
        text-align: center;
    }

    .summary h2 {
        color: #ff66b3;
        margin-bottom: 10px;
    }

    .summary p {
        font-size: 16px;
        margin: 5px 0;
        color: #333;
    }

    .transaction-table {
        background-color: #ffffff;
        padding: 20px;
        border-radius: 15px;
        box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
        width: 800px;
        text-align: center;
    }

    table {
        width: 100%;
        border-collapse: collapse;
    }

    th {
        background-color: #ffb3d9;
        color: white;
        padding: 10px;
    }

    td {
        padding: 10px;
        border-bottom: 1px solid #f2f2f2;
        color: #555;
    }

    tr:nth-child(even) {
        background-color: #fff5fa;
    }

    tr:hover {
        background-color: #ffe6f0;
    }

    .date-filter-form {
        background-color: #fff0f5;
        padding: 20px;
        border-radius: 15px;
        box-shadow: 0 4px 8px rgba(0,0,0,0.1);
        margin-bottom: 30px;
        width: 500px;
        text-align: center;
    }

    .date-filter-form form {
        display: flex;
        justify-content: space-between;
        align-items: center;
        gap: 10px;
    }

    .date-filter-form label {
        font-weight: bold;
        color: #ff66b3;
    }

    .date-filter-form input[type="date"] {
        padding: 10px;
        border: 2px solid #ff66b3;
        border-radius: 5px;
        font-size: 14px;
        width: 45%;
    }

    .date-filter-form input[type="submit"] {
        background-color: #ff66b3;
        color: white;
        padding: 10px 15px;
        border: none;
        border-radius: 5px;
        font-size: 14px;
        cursor: pointer;
        transition: background-color 0.3s ease;
    }

    .date-filter-form input[type="submit"]:hover {
        background-color: #ff4da1;
    }

    .error-message {
        color: #ff1a1a;
        margin: 10px 0;
        font-size: 14px;
        font-weight: bold;
        text-align: center;
    }

    .new-transaction-form {
        background-color: #fff0f5;
        padding: 25px;
        border-radius: 15px;
        box-shadow: 0 4px 8px rgba(0,0,0,0.1);
        margin-bottom: 30px;
        width: 500px;
        text-align: center;
    }

    .new-transaction-form h3 {
        color: #ff66b3;
        margin-bottom: 20px;
    }

    .new-transaction-form form {
        display: flex;
        flex-direction: column;
        gap: 10px;
    }

    .new-transaction-form input[type="text"],
    .new-transaction-form input[type="number"],
    .new-transaction-form input[type="datetime-local"],
    .new-transaction-form select {
        padding: 10px;
        border: 2px solid #ff66b3;
        border-radius: 5px;
        font-size: 14px;
    }

    .new-transaction-form input[type="submit"] {
        background-color: #ff66b3;
        color: white;
        padding: 10px;
        border: none;
        border-radius: 5px;
        font-size: 14px;
        cursor: pointer;
        transition: background-color 0.3s ease;
    }

    .new-transaction-form input[type="submit"]:hover {
        background-color: #ff4da1;
    }

    .delete-btn {
        background-color: #ff4d4d;
        color: white;
        border: none;
        padding: 6px 12px;
        border-radius: 5px;
        cursor: pointer;
        font-size: 14px;
        transition: background-color 0.3s ease;
    }

    .delete-btn:hover {
        background-color: #e60000;
    }
</style>