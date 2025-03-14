CREATE TABLE IF NOT EXISTS UserEntity
(
	userId              BIGSERIAL   PRIMARY KEY ,
	userNickname        TEXT NOT NULL ,
	email               TEXT NOT NULL ,
	userPassword        TEXT NOT NULL 
);

CREATE TABLE IF NOT EXISTS Category
(
	categoryId          BIGSERIAL PRIMARY KEY ,
	title               TEXT NOT NULL ,
	description         TEXT NULL 
);

CREATE TABLE IF NOT EXISTS Note
(
	noteId              BIGSERIAL PRIMARY KEY ,
	content             TEXT NOT NULL ,
	createdAt           DATE NULL ,
	updatedAt           DATE NULL ,
	userId              BIGSERIAL NOT NULL REFERENCES UserEntity(userId),
	categoryId          BIGSERIAL NOT NULL REFERENCES Category(categoryId)
);