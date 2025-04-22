CREATE TABLE Dino (
    IdDino SERIAL PRIMARY KEY,
    Weight INTEGER NOT NULL,
    DateOfBirthDino DATE NOT NULL,
    DateOfDeath DATE,
    KindOfDino VARCHAR(20) NOT NULL,
    NameDino VARCHAR(30) NOT NULL
);

 CREATE TABLE Employee (
    IdEmpl SERIAL PRIMARY KEY,
    SurnameEmpl VARCHAR(50) NOT NULL,
    NameEmpl VARCHAR(50) NOT NULL,
    PatronumicEmpl VARCHAR(50),
    DateOfBirthEmpl DATE
 );

 CREATE TABLE Incident (
    IdIncident SERIAL PRIMARY KEY,
    EmplId INTEGER REFERENCES Employee(IdEmpl),
    DinoId INTEGER REFERENCES Dino(IdDino),
    DateOfIncident DATE NOT NULL
 );

