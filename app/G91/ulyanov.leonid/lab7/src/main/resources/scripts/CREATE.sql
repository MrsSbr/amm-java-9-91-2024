CREATE TABLE IF NOT EXISTS "User"(
	Id_user serial PRIMARY KEY,
	Email varchar(254) NOT NULL, -- проверка: только латынь, цифры и символы, обязательно @
	"Password" text NOT NULL,
	LastName varchar(100) NOT NULL, -- проверка: только буквы
	FirstName varchar(100) NOT NULL,  -- проверка: только буквы
	PatronymicName varchar(100),  -- проверка: только буквы
	PhoneNumber char(11) NOT NULL -- проверка: начинается с 7
);

ALTER TABLE IF EXISTS "User"
ADD CONSTRAINT U_User_Email UNIQUE(Email);

CREATE TABLE IF NOT EXISTS Book(
	Id_book serial PRIMARY KEY,
	Title text NOT NULL,
	Author varchar(150) NOT NULL,
	Publisher varchar(100) NOT NULL,
	NumberOfPages smallint NOT NULL,
	PublishedYear smallint NOT NULL,
	BookType varchar(100) NOT NULL
);

CREATE TABLE IF NOT EXISTS BookUpdates(
	Id_update serial PRIMARY KEY,
	Id_book integer, 
	Id_user integer,
	UpdateTime timestamp NOT NULL,
	UpdateType varchar(100) NOT NULL
);

CREATE INDEX Ind_BookUpdate_Id_book ON BookUpdates(Id_book);
CREATE INDEX Ind_BookUpdate_Id_user ON BookUpdates(Id_user);

ALTER TABLE IF EXISTS BookUpdates
ADD CONSTRAINT FK_BookUpdates_Id_book 
FOREIGN KEY(Id_book) REFERENCES Book(Id_book)
ON DELETE RESTRICT
ON UPDATE CASCADE;

ALTER TABLE IF EXISTS BookUpdates
ADD CONSTRAINT FK_BookUpdates_Id_user 
FOREIGN KEY(Id_user) REFERENCES "User"(Id_user)
ON DELETE CASCADE
ON UPDATE CASCADE;

ALTER TABLE IF EXISTS BookUpdates
ALTER UpdateTime SET DEFAULT NOW();