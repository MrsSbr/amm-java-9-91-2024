CREATE TABLE User_Table
(
    User_ID       BIGSERIAL PRIMARY KEY,
    User_Name     VARCHAR(200) NOT NULL UNIQUE,
    User_Password VARCHAR(30)  NOT NULL,
    User_Role     VARCHAR(30),
    Phone         VARCHAR(11) UNIQUE,
    Birth_Date    DATE
);

CREATE TABLE RentalObject_Table
(
    Object_ID   BIGSERIAL PRIMARY KEY,
    Object_Name VARCHAR(200) NOT NULL,
    Object_Type VARCHAR(30)  NOT NULL,
    Object_Info TEXT,
    Price       INT -- rent price in rubles (for day)
);

CREATE TABLE Agreement_Table
(
    Agreement_ID BIGSERIAL PRIMARY KEY,
    User_ID      BIGINT REFERENCES User_Table (User_ID),
    Object_ID    BIGINT REFERENCES RentalObject_Table (Object_ID),
    Time_Start   DATE,
    Time_End     DATE,
    Sum_Price    INT -- sum price in rubles
);



