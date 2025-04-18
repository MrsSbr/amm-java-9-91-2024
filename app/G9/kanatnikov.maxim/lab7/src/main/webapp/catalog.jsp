<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Каталог игр</title>
    <style>
        * {
            box-sizing: border-box;
            font-family: 'Segoe UI', system-ui, sans-serif;
        }

        body {
            margin: 0;
            padding: 2rem;
            min-height: 100vh;
            background: linear-gradient(135deg, #f3e8ff 0%, #f8fafc 100%);
        }

        .header-bar {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 2rem;
        }

        h1 {
            color: #4c1d95;
            margin: 0;
            font-weight: 600;
            font-size: 1.8rem;
        }

        .logout-link {
            color: #7c3aed;
            text-decoration: none;
            font-weight: 600;
            padding: 0.5rem 1rem;
            border-radius: 6px;
            transition: all 0.3s ease;
        }

        .logout-link:hover {
            background: #f3e8ff;
        }

        .search-form {
            background: white;
            padding: 2rem;
            border-radius: 16px;
            box-shadow: 0 8px 24px rgba(106, 33, 182, 0.15);
            margin-bottom: 2rem;
        }

        .search-form h2 {
            color: #4c1d95;
            margin-top: 0;
            margin-bottom: 1.5rem;
            font-size: 1.4rem;
        }

        .form-controls {
            display: flex;
            gap: 1rem;
            flex-wrap: wrap;
            align-items: flex-end;
        }

        select, input[type="text"] {
            padding: 0.9rem;
            border: 2px solid #e2e8f0;
            border-radius: 8px;
            font-size: 1rem;
            min-width: 240px;
            transition: all 0.3s ease;
        }

        select:focus, input[type="text"]:focus {
            outline: none;
            border-color: #8b5cf6;
            box-shadow: 0 0 0 3px rgba(139, 92, 246, 0.2);
        }

        button[type="submit"] {
            padding: 0.9rem 1.8rem;
            background: #7c3aed;
            color: white;
            border: none;
            border-radius: 8px;
            font-weight: 600;
            cursor: pointer;
            transition: all 0.3s ease;
        }

        button[type="submit"]:hover {
            background: #6d28d9;
            transform: translateY(-1px);
        }

        .slider-container {
            background: #f8fafc;
            padding: 1.5rem;
            border-radius: 12px;
            margin: 1rem 0;
        }

        .multi-slider {
            -webkit-appearance: none;
            width: 100%;
            height: 6px;
            background: #e2e8f0;
            border-radius: 3px;
            outline: none;
            margin: 1rem 0;
        }

        .multi-slider::-webkit-slider-thumb {
            -webkit-appearance: none;
            width: 20px;
            height: 20px;
            background: #7c3aed;
            border-radius: 50%;
            cursor: pointer;
            transition: transform 0.2s ease;
        }

        .multi-slider::-webkit-slider-thumb:hover {
            transform: scale(1.1);
        }

        .values-display {
            color: #475569;
            font-size: 0.9rem;
            margin-bottom: 0.5rem;
        }

        table {
            width: 100%;
            border-collapse: collapse;
            background: white;
            border-radius: 16px;
            overflow: hidden;
            box-shadow: 0 8px 24px rgba(106, 33, 182, 0.1);
        }

        th, td {
            padding: 1rem;
            text-align: left;
            border-bottom: 1px solid #f1f5f9;
        }

        th {
            background-color: #7c3aed;
            color: white;
            font-weight: 600;
        }

        tr:hover {
            background-color: #f8fafc;
        }

        .history-link {
            display: inline-block;
            padding: 0.9rem 1.8rem;
            background: #7c3aed;
            color: white;
            text-decoration: none;
            border-radius: 8px;
            margin: 1.5rem 0;
            transition: all 0.3s ease;
        }

        .history-link:hover {
            background: #6d28d9;
            transform: translateY(-1px);
        }

        .error {
            color: #dc2626;
            background: #fee2e2;
            padding: 1rem;
            margin: 1rem 0;
            border-radius: 8px;
            border: 1px solid #fca5a5;
        }

        .empty-state {
            text-align: center;
            padding: 3rem;
            background: white;
            border-radius: 16px;
            color: #64748b;
            box-shadow: 0 8px 24px rgba(106, 33, 182, 0.1);
        }
    </style>
</head>
<body>
<div class="header-bar">
    <h1>Добро пожаловать, ${user.firstName}!</h1>
    <a href="/logout" class="logout-link">Выйти</a>
</div>

<div class="search-form">
    <h2>Поиск игр</h2>
    <form action="/catalog" method="get" id="searchForm">
        <div class="form-controls">
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
                        От <span id="minPriceValue">0</span> до <span id="maxPriceValue">19990</span> руб.
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
                        Возраст: <span id="ageValue">0</span> лет
                    </div>
                    <input type="range"
                           class="multi-slider"
                           id="minAge"
                           name="age"
                           min="0"
                           max="18"
                           value="${empty param.maxAge ? 0 : param.maxAge}">
                </div>
            </div>

            <button type="submit">Найти</button>
        </div>
    </form>

    <c:if test="${not empty errorMessage}">
        <div class="error">${errorMessage}</div>
    </c:if>
</div>

<a href="/history" class="history-link">История покупок</a>

<c:choose>
    <c:when test="${empty games}">
        <div class="empty-state">
            <p>Игр по вашему запросу не найдено</p>
        </div>
    </c:when>
    <c:otherwise>
        <table>
            <thead>
            <tr>
                <th>Название</th>
                <th>Цена</th>
                <th>Жанр</th>
                <th>Возраст</th>
                <th>Издатель</th>
                <th>Описание</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${games}" var="game">
                <tr onclick="confirmPurchase('${game.boardGameId}', '${game.price}')">
                    <td>${game.name}</td>
                    <td>${game.price} руб.</td>
                    <td>${game.genre}</td>
                    <td>от ${game.minAge}+</td>
                    <td>${game.publisher}</td>
                    <td>${game.description}</td>
                </tr>
            </c:forEach>
            </tbody>
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

    function confirmPurchase(gameId, price) {
        if (confirm('Купить игру за ' + price + ' руб.?')) {
            fetch('/purchase', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded',
                },
                body: 'gameId=' + gameId + '&price=' + price
            }).then(response => {
                if (response.ok) {
                    alert('Покупка успешно совершена!');
                    location.reload();
                } else {
                    alert('Ошибка при покупке');
                }
            });
        }
    }
</script>
</body>
</html>