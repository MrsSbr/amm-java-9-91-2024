
CREATE TABLE UserTable (
       UserID SERIAL PRIMARY KEY,
       UserName VARCHAR(30) NOT NULL UNIQUE,
       Password VARCHAR(50) NOT NULL,
       UserRole VARCHAR(5),
       Phone VARCHAR(11) UNIQUE,
       BirthDate DATE
);

CREATE TABLE RentalObjectTable (
        ObjectID SERIAL PRIMARY KEY,
        ObjectName VARCHAR(100) NOT NULL,
        ObjectType VARCHAR(10) NOT NULL,
        ObjectInfo TEXT,
        Price INTEGER -- rent price in rubles (for day)
);

CREATE TABLE AgreementTable (
        AgreementID SERIAL PRIMARY KEY,
        UserID INTEGER REFERENCES UserTable (UserID),
        ObjectID INTEGER REFERENCES RentalObjectTable (ObjectID),
        EventName VARCHAR(100) NOT NULL,
        EventInfo TEXT,
        TimeBegin DATE,
        TimeEnd DATE,
        SumPrice INT -- sum price in rubles
);



