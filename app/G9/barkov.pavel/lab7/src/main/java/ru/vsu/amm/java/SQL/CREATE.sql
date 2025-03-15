CREATE TABLE shareholder
(
    id SERIAL PRIMARY KEY,
    password VARCHAR(64) NOT NULL,
    fio VARCHAR(200) NOT NULL CHECK (fio ~* '^[А-ЯЁа-яё ]+$'),
    document VARCHAR(20) NOT NULL UNIQUE CHECK (document ~* '^[0-9 ]+$'),
    email VARCHAR(32) UNIQUE CHECK (phone ~* '^[0-9+()]+$'),
    phone VARCHAR(16) UNIQUE CHECK (email ~ '^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Z|a-z]{2,}$')
);

CREATE TABLE stocks
(
    id SERIAL PRIMARY KEY,
    price DECIMAL(19,4) NOT NULL CHECK (price > 0),
    count INT NOT NULL CHECK (count > 0)
    name VARCHAR(100) NOT NULL UNIQUE CHECK (name ~* '^[А-ЯЁа-яёa-zA-Z0-9 ]+$'),
    dividends DECIMAL(19,4) CHECK (dividends > 0)
);

CREATE TABLE shareholder_stocks
(
    shareholder_id SERIAL REFERENCES shareholder(id),
    stock_id SERIAL REFERENCES stocks(id),
    count INT NOT NULL CHECK (count > 0),
    PRIMARY KEY(shareholder_id,stock_id)
);