CREATE TABLE IF NOT EXISTS UserEntity (
    User_Id      SERIAL      PRIMARY KEY,
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
    Board_Game_Id SERIAL  PRIMARY KEY,
    "Name"        TEXT    NOT NULL,
    Price         INTEGER NOT NULL,
    Genre         TEXT    NOT NULL,
    Min_Age       INTEGER,
    Publisher     TEXT    NOT NULL,
    Description   TEXT
);

CREATE INDEX Game_Price_Index
    ON BoardGame(Price);

CREATE TABLE IF NOT EXISTS PurchaseHistory (
    Purchase_History_Id SERIAL  PRIMARY KEY,
    Order_Number        INTEGER NOT NULL,
    Payment             INTEGER NOT NULL,
    User_Id             INTEGER NOT NULL REFERENCES UserEntity(User_Id),
    Board_Game_Id       INTEGER NOT NULL REFERENCES BoardGame(Board_Game_Id)
);