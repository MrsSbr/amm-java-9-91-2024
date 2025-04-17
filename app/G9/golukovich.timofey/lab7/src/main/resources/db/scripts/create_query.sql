CREATE TABLE hotel(
    hotel_id        SERIAL          PRIMARY KEY,
    hotel_name      VARCHAR(200)    NOT NULL,
    address         TEXT            NOT NULL,
    email           VARCHAR(30)     NOT NULL,
    phone_number    CHARACTER(12)   NOT NULL,
    opening_date    DATE            NOT NULL
);

ALTER TABLE hotel
    ADD CONSTRAINT Unique_Hotel_Address
        UNIQUE(address);

ALTER TABLE hotel
    ADD CONSTRAINT Unique_Hotel_Email
        UNIQUE(email);

ALTER TABLE hotel
    ADD CONSTRAINT Unique_Hotel_Phone_number
        UNIQUE(phone_number);
ALTER TABLE hotel
    ADD CHECK(phone_number ~ '^\+7[0-9]{10}$');

CREATE TABLE hotel_room(
     room_id         SERIAL      PRIMARY KEY,
     hotel_id        INTEGER     NOT NULL REFERENCES hotel(hotel_id),
     room_number     INTEGER     NOT NULL,
     floor_number    INTEGER     NOT NULL,
     beds_count      INTEGER     NOT NULL,
     specifications  TEXT
);

ALTER TABLE hotel_room
    ADD CONSTRAINT Unique_Hotel_room_Number_Floor
        UNIQUE(hotel_id, room_number, floor_number);

CREATE TABLE employee(
   employee_id        	SERIAL			PRIMARY KEY,
   hotel_id           	INTEGER			REFERENCES hotel(hotel_id),
   "name"             	VARCHAR(100),
   phone_number       	CHARACTER(12),
   email              	VARCHAR(30),
   post               	VARCHAR(100)	NOT NULL,
   passport_number    	CHARACTER(4),
   passport_series    	CHARACTER(6),
   salary             	INTEGER,
   birthday           	DATE,
   login              	VARCHAR(50)     NOT NULL,
   "password"			TEXT            NOT NULL
);

ALTER TABLE employee
    ADD CHECK(phone_number ~ '^\+7[0-9]{10}$');

ALTER TABLE employee
    ADD CONSTRAINT Unique_passport_data
        UNIQUE(passport_number, passport_series);

ALTER TABLE employee
    ADD CHECK(salary > 0);