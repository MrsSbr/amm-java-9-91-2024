CREATE TABLE "User"
(	
	Id_user SERIAL PRIMARY KEY,
	LastName TEXT NOT NULL,
	FirstName TEXT NOT NULL,
	Patronymic TEXT,
	Login TEXT NOT NULL UNIQUE,
	"Password" TEXT NOT NULL,
	"Role" TEXT NOT NULL
);

CREATE TABLE Vehicle
(
	Id_vehicle SERIAL PRIMARY KEY,
	RegistrationNumber TEXT NOT NULL,
	Model TEXT NOT NULL,
	Brand TEXT NOT NULL,
	Colour TEXT NOT NULL
);

CREATE TABLE "Session"
(	
	Id_session SERIAL PRIMARY KEY,
	Id_user INTEGER NOT NULL REFERENCES "User"(Id_user),
	Id_vehicle INTEGER NOT NULL REFERENCES Vehicle(Id_vehicle),
	ParkingPrice DECIMAL(10, 2) NOT NULL,
	EntryDate TIMESTAMP NOT NULL,
	ExitDate TIMESTAMP
);

CREATE INDEX I_Session_Id_user ON "Session"(Id_user);
CREATE INDEX I_Session_Id_vehicle ON "Session"(Id_vehicle);