CREATE TABLE shareholder
(
    id SERIAL PRIMARY KEY,
    password TEXT NOT NULL,
    fio VARCHAR(200) NOT NULL,
    document VARCHAR(20) NOT NULL UNIQUE,
    email VARCHAR(32) UNIQUE,
    phone VARCHAR(16) UNIQUE
);

CREATE TABLE stocks
(
    id SERIAL PRIMARY KEY,
    price DECIMAL(19,4) NOT NULL,
    count INT NOT NULL,
    name VARCHAR(100) NOT NULL UNIQUE,
    dividends DECIMAL(19,4)
);

CREATE TABLE shareholder_stocks
(
    shareholder_id INT REFERENCES shareholder(id),
    stock_id INT REFERENCES stocks(id),
    count INT NOT NULL,
    PRIMARY KEY(shareholder_id,stock_id)
);