CREATE TABLE IF NOT EXISTS Users(
    User_id BIGSERIAL PRIMARY KEY,
    Surname VARCHAR(200) NOT NULL,
    name VARCHAR(200) NOT NULL,
    Patronymicname VARCHAR(200),
    Phone_number VARCHAR(14) NOT NULL,
    Email VARCHAR(300) NOT NULL,
    Password TEXT NOT NULL
);

ALTER TABLE IF EXISTS Users
    ADD CONSTRAINT U_UsersFullName UNIQUE(Surname, name, Patronymicname);

ALTER TABLE IF EXISTS Users
    ADD CONSTRAINT U_UsersEmail UNIQUE(Email);

CREATE TABLE IF NOT EXISTS Smartphones(
    Smartphone_id BIGSERIAL PRIMARY KEY,
    Brand VARCHAR(100) NOT NULL,
    Model VARCHAR(200) NOT NULL,
    Ram INTEGER NOT NULL,
    Storage_memory INTEGER NOT NULL,
    Main_camera_resolution NUMERIC(5, 1) NOT NULL,
    Screen_size NUMERIC(5, 2) NOT NULL,
    Color VARCHAR(100),
    Price NUMERIC(10, 2) NOT NULL,
    Amount INTEGER NOT NULL
);

CREATE TABLE IF NOT EXISTS Orders(
    Order_id BIGSERIAL PRIMARY KEY,
    User_id BIGINT NOT NULL,
    Smartphone_id BIGINT NOT NULL,
    Is_paid BOOLEAN NOT NULL,
    Registration_date TIMESTAMP
);


ALTER TABLE IF EXISTS Orders
    ADD CONSTRAINT FK_OrdersUserID FOREIGN KEY(User_id) REFERENCES Users(User_id);

ALTER TABLE IF EXISTS Orders
    ADD CONSTRAINT Fk_OrdersSmartphoneID FOREIGN KEY(Smartphone_id) REFERENCES Smartphones(Smartphone_id);