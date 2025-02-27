
CREATE TABLE UserTable (
       UserID SERIAL PRIMARY KEY,
       UserName VARCHAR(200) NOT NULL,
       Password VARCHAR(30) NOT NULL,
       UserRole VARCHAR(50),
       Phone VARCHAR(11),
       BirthDate DATE
);

ALTER TABLE UserTable
    ADD CONSTRAINT UserName_Unique UNIQUE (UserName);

ALTER TABLE UserTable
    ADD CONSTRAINT Phone_Unique UNIQUE (Phone);

CREATE TABLE EventTable (
        EventID SERIAL PRIMARY KEY,
        EventName VARCHAR(200) NOT NULL,
        EventInfo TEXT,
        TimeBegin DATE,
        TimeEnd DATE,
        PeopleCount INT,
        Price INT -- participation price in rubles
);

CREATE TABLE ItemTable (
       ItemID SERIAL PRIMARY KEY,
       ItemName VARCHAR(100) NOT NULL,
       ItemInfo TEXT,
       Price INT -- rental price for day in rubles
);

CREATE TABLE HabitationTable (
        HabitationID SERIAL PRIMARY KEY,
        HabitationName VARCHAR(100) NOT NULL,
        HabitationInfo TEXT,
        Price INT -- booking price for day in rubles
);

