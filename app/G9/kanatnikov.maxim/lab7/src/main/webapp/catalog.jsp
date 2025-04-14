<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Каталог игр</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 20px;
        }
        .search-form {
            margin-bottom: 20px;
            padding: 15px;
            background: #f5f5f5;
            border-radius: 5px;
        }
        .search-form select, .search-form input {
            padding: 8px;
            margin-right: 10px;
        }
        .search-form button {
            padding: 8px 15px;
            background: #4CAF50;
            color: white;
            border: none;
            border-radius: 3px;
        }
        .search-form button:hover {
            background: #45a049;
        }
        table {
            border-collapse: collapse;
            width: 100%;
            margin-top: 20px;
        }
        th, td {
            padding: 12px;
            text-align: left;
            border-bottom: 1px solid #ddd;
        }
        th {
            background-color: #4CAF50;
            color: white;
        }
        tr:hover {
            background-color: #f5f5f5;
        }
        .error {
            color: red;
            margin: 10px 0;
        }
        .slider-container {
            position: relative;
            width: 300px;
            margin: 25px 0 40px;
        }
        .multi-slider {
            -webkit-appearance: none;
            width: 100%;
            height: 5px;
            background: #ddd;
            outline: none;
            margin: 15px 0;
        }
        .multi-slider::-webkit-slider-thumb {
            -webkit-appearance: none;
            width: 20px;
            height: 20px;
            background: #4CAF50;
            border-radius: 50%;
            cursor: pointer;
        }
        .values-display {
            display: flex;
            justify-content: space-between;
            margin-bottom: 25px;
        }
    </style>
</head>
<body>
<h1>Добро пожаловать, ${user.firstName}!</h1>
<a href="/logout" style="float: right;">Выйти</a>

<div class="search-form">
    <h2>Поиск игр</h2>
    <form action="/catalog" method="get" id="searchForm">
        <select name="searchType" id="searchType" onchange="updateSearchUI()">
            <option value="name" ${param.searchType == 'name' ? 'selected' : ''}>Название</option>
            <option value="genre" ${param.searchType == 'genre' ? 'selected' : ''}>Жанр</option>
            <option value="publisher" ${param.searchType == 'publisher' ? 'selected' : ''}>Издатель</option>
            <option value="price" ${param.searchType == 'price' ? 'selected' : ''}>Диапазон цен</option>
            <option value="minAge" ${param.searchType == 'minAge' ? 'selected' : ''}>Ваш возраст</option>
        </select>

        <div id="searchInputs">
            <input type="text"
                   name="searchValue"
                   id="textInput"
                   placeholder="Введите значение"
                   value="${param.searchValue}"
                   style="display: none;">

            <div id="priceSlider" class="slider-container" style="display: none;">
                <div class="values-display">
                    <span id="minPriceValue">0</span> руб.
                    <span id="maxPriceValue">19990</span> руб.
                </div>
                <input type="range"
                       class="multi-slider"
                       id="priceMin"
                       name="minPrice"
                       min="0"
                       max="19990"
                       value="${empty param.minPrice ? 0 : param.minPrice}">
                <input type="range"
                       class="multi-slider"
                       id="priceMax"
                       name="maxPrice"
                       min="0"
                       max="19990"
                       value="${empty param.maxPrice ? 19990 : param.maxPrice}">
            </div>

            <div id="ageSlider" class="slider-container" style="display: none;">
                <div class="values-display">
                    <span id="ageValue">0</span> лет
                </div>
                <input type="range"
                       class="multi-slider"
                       id="minAge"
                       name="maxAge"
                       min="0"
                       max="18"
                       value="${empty param.maxAge ? 0 : param.maxAge}">
            </div>
        </div>

        <button type="submit">Найти</button>
    </form>

    <c:if test="${not empty errorMessage}">
        <div class="error">${errorMessage}</div>
    </c:if>
</div>

<c:choose>
    <c:when test="${empty games}">
        <p>Игр по вашему запросу не найдено</p>
    </c:when>
    <c:otherwise>
        <table>
            <tr>
                <th>ID</th>
                <th>Название</th>
                <th>Цена (руб)</th>
                <th>Жанр</th>
                <th>Мин. возраст</th>
                <th>Издатель</th>
                <th>Описание</th>
            </tr>
            <c:forEach items="${games}" var="game">
                <tr>
                    <td>${game.boardGameId}</td>
                    <td>${game.name}</td>
                    <td>${game.price}</td>
                    <td>${game.genre}</td>
                    <td>от ${game.minAge} лет</td>
                    <td>${game.publisher}</td>
                    <td>${game.description}</td>
                </tr>
            </c:forEach>
        </table>
    </c:otherwise>
</c:choose>

<script>
    window.onload = function() {
        updateSearchUI();
        initPriceSlider();
        initAgeSlider();
    };

    function updateSearchUI() {
        const searchType = document.getElementById('searchType').value;
        document.getElementById('textInput').style.display = 'none';
        document.getElementById('priceSlider').style.display = 'none';
        document.getElementById('ageSlider').style.display = 'none';

        if(searchType === 'price') {
            document.getElementById('priceSlider').style.display = 'block';
        } else if(searchType === 'minAge') {
            document.getElementById('ageSlider').style.display = 'block';
        } else {
            document.getElementById('textInput').style.display = 'inline';
        }
    }

    function initPriceSlider() {
        const minSlider = document.getElementById('priceMin');
        const maxSlider = document.getElementById('priceMax');
        const minValue = document.getElementById('minPriceValue');
        const maxValue = document.getElementById('maxPriceValue');

        minSlider.oninput = function() {
            if(parseInt(this.value) > parseInt(maxSlider.value)) {
                maxSlider.value = this.value;
            }
            updatePriceDisplay();
        };

        maxSlider.oninput = function() {
            if(parseInt(this.value) < parseInt(minSlider.value)) {
                minSlider.value = this.value;
            }
            updatePriceDisplay();
        };

        function updatePriceDisplay() {
            minValue.textContent = minSlider.value;
            maxValue.textContent = maxSlider.value;
        }

        updatePriceDisplay();
    }

    function initAgeSlider() {
        const ageSlider = document.getElementById('minAge');
        const ageValue = document.getElementById('ageValue');

        const formatAge = (value) => {
            return value === '18' ? '18+' : value;
        };

        ageSlider.oninput = function() {
            ageValue.textContent = formatAge(this.value);
        };

        ageValue.textContent = formatAge(ageSlider.value);
    }
</script>
</body>
</html>