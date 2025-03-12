
CREATE TABLE UserTable (
       UserID BIGINT PRIMARY KEY,
       UserName VARCHAR(200) NOT NULL UNIQUE,
       Password VARCHAR(30) NOT NULL,
       UserRole VARCHAR(30),
       Phone VARCHAR(11) UNIQUE,
       BirthDate DATE
);

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



