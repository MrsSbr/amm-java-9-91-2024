CREATE TABLE IF NOT EXISTS Users(
    UserID BIGSERIAL PRIMARY KEY,
    Surname VARCHAR(200) NOT NULL,
    "Name" VARCHAR(200) NOT NULL,
    Patronymicname VARCHAR(200),
    PhoneNumber VARCHAR(14) NOT NULL,
    Email VARCHAR(300) NOT NULL,
    "Password" TEXT NOT NULL,
    Birthday DATE NOT NULL,
    "Role" TEXT NOT NULL
);

ALTER TABLE IF EXISTS Users
ADD CONSTRAINT U_UsersFullName UNIQUE(Surname, "Name", Patronymicname);

ALTER TABLE IF EXISTS Users
ADD CONSTRAINT U_UsersEmail UNIQUE(Email);

ALTER TABLE IF EXISTS Users
ADD CONSTRAINT U_UsersPhoneNumber UNIQUE(PhoneNumber);

CREATE TABLE IF NOT EXISTS Smartphones(
    SmartphoneID SERIAL PRIMARY KEY,
    Brand VARCHAR(100) NOT NULL,
    Model VARCHAR(200) NOT NULL,
    RAM INTEGER NOT NULL,
    StorageMemory INTEGER NOT NULL,
    MainCameraResolution NUMERIC(,1) NOT NULL,
    ScreenSize NUMERIC(,2) NOT NULL,
    Color VARCHAR(100),
    Price MONEY NOT NULL,
    Amount INTEGER NOT NULL
);

CREATE TABLE IF NOT EXISTS Orders(
    OrderNum BIGINT PRIMARY KEY,
    UserID BIGINT NOT NULL,
    TotalCost MONEY NOT NULL,
    RegiatrationDate TIMESTAMP
);

CREATE TABLE IF NOT EXISTS SmartphonesInOrders(
    OrderNum BIGINT,
    SmartphoneID INTEGER,
    Amount INTEGER NOT NULL,
);

ALTER TABLE IF EXISTS SmartphonesInOrders
ADD CONSTRAINT PK_SmartphonesInOrders PRIMARY KEY(OrderNum, SmartphoneID);

ALTER TABLE IF EXISTS Orders
ADD CONSTRAINT FK_OrdersUserID FOREIGN KEY(UserID) REFERENCES Users(UserID);

ALTER TABLE IF EXISTS SmartphonesInOrders
ADD CONSTRAINT FK_SmartphonesInOrdersOrderNum FOREIGN KEY(OrderNum) REFERENCES Orders(OrderNum);

ALTER TABLE IF EXISTS SmartphonesInOrders
ADD CONSTRAINT FK_SmartphonesInOrdersSmartphoneID FOREIGN KEY(SmartphoneID) REFERENCES Smartphones(SmartphoneID);

