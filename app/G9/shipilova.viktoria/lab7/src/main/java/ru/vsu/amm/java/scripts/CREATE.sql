CREATE TABLE Users
(
	UserID INTEGER NOT NULL,
	PasswordHash INTEGER NOT NULL,
	PhoneNumber VARCHAR(15) NOT NULL,
	Email VARCHAR(50),
	Login VARCHAR(20) NOT NULL,
	CONSTRAINT PKUserID PRIMARY KEY (UserID),
	CONSTRAINT UQUserID UNIQUE(UserID),
	CONSTRAINT UQPasswordHash UNIQUE(PasswordHash),
	CONSTRAINT UQPhoneNumber UNIQUE(PhoneNumber),
	CONSTRAINT UQLogin UNIQUE(Login),
	CONSTRAINT CHKEmail CHECK (Email ~ '^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,}$'),
	CONSTRAINT CHKPhoneNumber CHECK (PhoneNumber SIMILAR TO '+7[0-9]{10}')
);

CREATE TABLE NextDestination
(
	NextDestinationID INTEGER NOT NULL ,
	NextDestinationName VARCHAR(100) NOT NULL ,
	Description VARCHAR(255),
	CONSTRAINT PKNextDestinationID PRIMARY KEY (NextDestinationID),
	CONSTRAINT UQNextDestinationID UNIQUE(NextDestinationID),
	CONSTRAINT UQNextDestinationName UNIQUE(NextDestinationName)
);

CREATE TABLE PropertyType
(
	PropertyTypeID INTEGER NOT NULL,
	PropertyTypeName VARCHAR(50) NOT NULL,
	NextDestinationID INTEGER NOT NULL,
	LastOfStorageDate DATE NOT NULL,
	StorageCost INTEGER NOT NULL,
	CONSTRAINT PKPropertyType PRIMARY KEY (PropertyTypeID),
	CONSTRAINT FKPropertyTypeNextDestination FOREIGN KEY (NextDestinationID) 
		REFERENCES NextDestination (NextDestinationID),
	CONSTRAINT UQPropertyTypeID UNIQUE(PropertyTypeID),
	CONSTRAINT UQPropertyTypeName UNIQUE(PropertyTypeName)
);

CREATE TABLE FoundProperties
(
	FoundPropertyID INTEGER NOT NULL,
	PropertyTypeID INTEGER NOT NULL ,
	DateOfFinding DATE NOT NULL,
	TimeOfFinding TIME NOT NULL DEFAULT CURRENT_TIME,
	ReturnStatus VARCHAR(20) NOT NULL DEFAULT 'Not returned',
	PlaceOfFinding VARCHAR(100),
	Description VARCHAR(100),
	UserID INTEGER NOT NULL,
	CONSTRAINT PKFoundProperties PRIMARY KEY (FoundPropertyID),
	CONSTRAINT FKFoundPropertiesPropertyType FOREIGN KEY (PropertyTypeID) 
		REFERENCES PropertyType (PropertyTypeID) ON DELETE SET NULL,
	CONSTRAINT FKFoundPropertiesUsers FOREIGN KEY (UserID) 
		REFERENCES Users (UserID) ON DELETE SET NULL,
	CONSTRAINT UQFoundPropertyID UNIQUE(FoundPropertyID),
	CONSTRAINT CHKDateOfFinding CHECK(DateOfFinding <= CURRENT_DATE)
);

CREATE INDEX IDXUserID ON Users(UserID);
CREATE INDEX IDXFoundPropertyID ON FoundProperties(FoundPropertyID);
CREATE INDEX IDXNextDestinationID ON NextDestination(NextDestinationID);
CREATE INDEX IDXPropertyTypeID ON PropertyType(PropertyTypeID);
CREATE INDEX IDXFoundPropertiesPropertyTypeID ON FoundProperties(PropertyTypeID);
CREATE INDEX IDXFoundPropertiesUserID ON FoundProperties(UserID);
CREATE INDEX IDXPropertyTypeNextDestinationID ON PropertyType(NextDestinationID);

