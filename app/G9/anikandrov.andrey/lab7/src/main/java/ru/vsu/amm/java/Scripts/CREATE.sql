
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

CREATE TABLE RentalObjectTable (
        ObjectID SERIAL PRIMARY KEY,
        ObjectName VARCHAR(200) NOT NULL,
        ObjectType VARCHAR(10) NOT NULL,
        ObjectInfo TEXT,
        Price INT -- rent price in rubles (for day)
);

CREATE TABLE AgreementTable (
        AgreementID SERIAL PRIMARY KEY,
        UserID INTEGER REFERENCES UserTable (UserID),
        ObjectID INTEGER REFERENCES RentalObjectTable (ObjectID),
        EventName VARCHAR(200) NOT NULL,
        EventInfo TEXT,
        TimeBegin DATE,
        TimeEnd DATE,
        SumPrice INT -- sum price in rubles
);



