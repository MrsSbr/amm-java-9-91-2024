CREATE TABLE Hotel(
    Hotel_id		SERIAL			PRIMARY KEY,
    Hotel_name	    VARCHAR(200)	NOT NULL,
    Address		    VARCHAR(100) 	NOT NULL,
    Email			VARCHAR(30)		NOT NULL,
    Phone_number	CHARACTER(12)	NOT NULL,
    Opening_date	DATE			NOT NULL
);

ALTER TABLE Hotel
    ADD CONSTRAINT Unique_Hotel_Address
        UNIQUE(Address);

ALTER TABLE Hotel
    ADD CONSTRAINT Unique_Hotel_Email
        UNIQUE(Email);
ALTER TABLE Hotel
    ADD CHECK(Email LIKE '_%@_%._%');

ALTER TABLE Hotel
    ADD CONSTRAINT Unique_Hotel_Phone_number
        UNIQUE(Phone_number);
ALTER TABLE Hotel
    ADD CHECK(Phone_number ~ '^\+7[0-9]{10}$');

ALTER TABLE Hotel
    ADD CHECK(Opening_date BETWEEN '1965-01-01' AND NOW());

CREATE TABLE Hotel_room(
    Room_id 		SERIAL 		PRIMARY KEY,
    Hotel_id 		INTEGER     NOT NULL REFERENCES Hotel(Hotel_id),
    Room_number		INTEGER		NOT NULL,
    Floor_number 	INTEGER 	NOT NULL,
    Beds_count 		INTEGER 	NOT NULL,
    Specifications 	VARCHAR(300)
);

ALTER TABLE Hotel_room
    ADD CONSTRAINT Unique_Hotel_room_Number_Floor
        UNIQUE(Hotel_id, Room_number, Floor_number);

ALTER TABLE Hotel_room
    ADD CHECK(Beds_count BETWEEN 0 AND 8);

ALTER TABLE Hotel_room
    ADD CHECK(Floor_number BETWEEN -5 AND 40);

CREATE TABLE Employee(
    Employee_id			SERIAL			PRIMARY KEY,
    Hotel_id			INTEGER			NOT NULL REFERENCES Hotel(Hotel_id),
    "Name"				VARCHAR(100)	NOT NULL,
    Phone_number 		CHARACTER(12) 	NOT NULL,
    Email 				VARCHAR(30) 	NOT NULL,
    Post				VARCHAR(100)	NOT NULL,
    Passport_number		CHARACTER(4)	NOT NULL,
    Passport_series		CHARACTER(6)	NOT NULL,
    Salary				INTEGER			NOT NULL
);

ALTER TABLE Employee
    ADD CHECK(Phone_number ~ '^\+7[0-9]{10}$');

ALTER TABLE Employee
    ADD CHECK(Email LIKE '_%@_%._%');

ALTER TABLE Employee
    ADD CONSTRAINT Unique_passport_data
        UNIQUE(Passport_number, Passport_series);
ALTER TABLE Employee
    ADD CHECK(Passport_number ~ '^\[1-9][0-9]{3}$');
ALTER TABLE Employee
    ADD CHECK(Passport_series ~ '^\[1-9][0-9]{5}$');

ALTER TABLE Employee
    ADD CHECK(Salary > 0);