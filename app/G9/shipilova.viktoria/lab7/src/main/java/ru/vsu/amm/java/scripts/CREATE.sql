CREATE TABLE Users
(
	UserID BIGSERIAL PRIMARY KEY,
	Password TEXT NOT NULL,
	PhoneNumber TEXT NOT NULL UNIQUE,
	Email TEXT,
	Login TEXT NOT NULL UNIQUE
);

CREATE TABLE NextDestination
(
	NextDestinationID BIGSERIAL PRIMARY KEY,
	NextDestinationName TEXT NOT NULL UNIQUE,
	Description TEXT
);

CREATE TABLE PropertyType
(
	PropertyTypeID BIGSERIAL PRIMARY KEY,
	PropertyTypeName TEXT NOT NULL UNIQUE,
	NextDestinationID BIGINT NOT NULL REFERENCES NextDestination (NextDestinationID),
	StorageDays INTEGER NOT NULL DEFAULT 60, -- Maximum number of days of storage for this type
	StorageCost INTEGER NOT NULL DEFAULT 100 -- Cost in rubles
);

CREATE TABLE FoundProperties
(
	FoundPropertyID BIGSERIAL PRIMARY KEY,
	PropertyTypeID BIGINT NOT NULL REFERENCES PropertyType (PropertyTypeID),
	DateOfFinding DATE NOT NULL DEFAULT NOW(),
	TimeOfFinding TIME,
	ReturnStatus TEXT NOT NULL DEFAULT 'NOT_RETURNED',
	PlaceOfFinding TEXT,
	Description TEXT,
	UserID BIGINT REFERENCES Users (UserID)
);

CREATE INDEX IDXFoundPropertiesPropertyTypeID ON FoundProperties(PropertyTypeID);
CREATE INDEX IDXFoundPropertiesUserID ON FoundProperties(UserID);
CREATE INDEX IDXPropertyTypeNextDestinationID ON PropertyType(NextDestinationID);