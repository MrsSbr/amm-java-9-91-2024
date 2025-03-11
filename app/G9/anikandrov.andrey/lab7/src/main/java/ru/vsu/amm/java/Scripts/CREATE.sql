
CREATE TABLE UserTable (
       UserID BIGINT PRIMARY KEY,
       UserName VARCHAR(200) NOT NULL,
       Password VARCHAR(30) NOT NULL,
       UserRole VARCHAR(30),
       Phone VARCHAR(11),
       BirthDate DATE
);

ALTER TABLE UserTable
    ADD CONSTRAINT UserName_Unique UNIQUE (UserName);

ALTER TABLE UserTable
    ADD CONSTRAINT Phone_Unique UNIQUE (Phone);

CREATE TABLE RentalObjectTable (
        ObjectID BIGINT PRIMARY KEY,
        ObjectName VARCHAR(200) NOT NULL,
        ObjectType VARCHAR(30) NOT NULL,
        ObjectInfo TEXT,
        Price INT -- rent price in rubles (for day)
);

CREATE TABLE AgreementTable (
        AgreementID BIGINT PRIMARY KEY,
        UserID BIGINT REFERENCES UserTable (UserID),
        ObjectID BIGINT REFERENCES RentalObjectTable (ObjectID),
        EventName VARCHAR(200) NOT NULL,
        EventInfo TEXT,
        TimeStart DATE,
        TimeEnd DATE,
        SumPrice INT -- sum price in rubles
);



