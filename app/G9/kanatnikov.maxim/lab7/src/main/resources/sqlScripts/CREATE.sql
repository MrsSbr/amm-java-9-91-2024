CREATE TABLE IF NOT EXISTS UserEntity (
    User_Id      BIGSERIAL   PRIMARY KEY,
    First_Name   TEXT        NOT NULL,
    Last_Name    TEXT        NOT NULL,
    Patronymic   TEXT,
    City         TEXT        NOT NULL,
    Email        TEXT        NOT NULL,
    Phone_Number VARCHAR(12) NOT NULL,
    "Password"   TEXT        NOT NULL,
    "Role"       TEXT        NOT NULL
);

CREATE TABLE IF NOT EXISTS BoardGame (
    Board_Game_Id BIGSERIAL PRIMARY KEY,
    "Name"        TEXT      NOT NULL,
    Price         INTEGER   NOT NULL,
    Genre         TEXT      NOT NULL,
    Min_Age       INTEGER,
    Publisher     TEXT      NOT NULL,
    Description   TEXT
);

CREATE INDEX Game_Price_Index
    ON BoardGame(Price);

CREATE INDEX Game_Genre_Index
    ON BoardGame(Genre);

CREATE TABLE IF NOT EXISTS PurchaseHistory (
    Order_Number  BIGSERIAL PRIMARY KEY,
    Payment       INTEGER   NOT NULL,
    User_Id       BIGINT    NOT NULL REFERENCES UserEntity(User_Id),
    Board_Game_Id BIGINT    NOT NULL REFERENCES BoardGame(Board_Game_Id)
);
