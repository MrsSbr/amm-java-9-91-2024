CREATE TABLE Client (
    Id_client BIGSERIAL PRIMARY KEY,
    Email TEXT UNIQUE NOT NULL,
    Name TEXT NOT NULL,
    Password TEXT NOT NULL
);

CREATE TABLE Psychologist (
    Id_psychologist BIGSERIAL PRIMARY KEY,
    Name TEXT NOT NULL,
    Surname TEXT NOT NULL,
    Birthdate DATE NOT NULL,
    Gender VARCHAR(1), 
    Experience SMALLINT,
    Login TEXT UNIQUE NOT NULL,
    Password TEXT NOT NULL
);

CREATE TABLE Session (
    Id_session BIGSERIAL PRIMARY KEY,
    Id_psychologist BIGINT NOT NULL,
    Id_client BIGINT NOT NULL,
    Date TIMESTAMP NOT NULL,
    Price DECIMAL(10,2),
    Duration SMALLINT,
    FOREIGN KEY (Id_psychologist) REFERENCES Psychologist(Id_psychologist) ON DELETE SET NULL,
    FOREIGN KEY (Id_client) REFERENCES Client(Id_client) ON DELETE SET NULL
);

CREATE INDEX idx_session_psychologist ON Session (Id_psychologist);
CREATE INDEX idx_session_client ON Session (Id_client);
