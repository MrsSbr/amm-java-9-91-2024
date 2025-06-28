CREATE TABLE IF NOT EXISTS hotel(
    hotel_id        SERIAL          PRIMARY KEY,
    hotel_name      VARCHAR(200)    NOT NULL,
    address         TEXT            NOT NULL,
    email           VARCHAR(30)     NOT NULL,
    phone_number    CHARACTER(12)   NOT NULL,
    opening_date    DATE            NOT NULL,

    CHECK (phone_number ~ '^\+7[0-9]{10}$'),

    CONSTRAINT  unique_Hotel_Name           UNIQUE  (hotel_name),
    CONSTRAINT  unique_Hotel_Address        UNIQUE  (address),
    CONSTRAINT  unique_Hotel_Email          UNIQUE  (email),
    CONSTRAINT  unique_Hotel_Phone_number   UNIQUE  (phone_number)
);

CREATE TABLE IF NOT EXISTS hotel_room(
    room_id         SERIAL      PRIMARY KEY,
    hotel_id        INTEGER     NOT NULL REFERENCES hotel(hotel_id),
    room_number     INTEGER     NOT NULL,
    floor_number    INTEGER     NOT NULL,
    beds_count      INTEGER     NOT NULL,
    specifications  TEXT,
    
    CONSTRAINT unique_Hotel_room_Number_Floor UNIQUE (hotel_id, room_number, floor_number)
);

CREATE TABLE IF NOT EXISTS employee(
    employee_id        	SERIAL			PRIMARY KEY,
    hotel_id           	INTEGER         REFERENCES hotel(hotel_id),
    name             	VARCHAR(100)    NULL,
    phone_number       	CHARACTER(12)   NULL,
    email              	VARCHAR(30)     NULL,
    post               	VARCHAR(100)	NOT NULL,
    passport_number    	CHARACTER(4)    NULL,
    passport_series    	CHARACTER(6)    NULL,
    salary             	INTEGER         NULL,
    birthday           	DATE            NULL,
    login              	VARCHAR(50)     NOT NULL,
    password			TEXT            NOT NULL,

    CONSTRAINT  unique_passport_data        UNIQUE  (passport_number, passport_series),
    CONSTRAINT  salary_check                CHECK   (salary IS NULL OR salary >= 0),
    CONSTRAINT  phone_number_format_check   CHECK   (phone_number ~ '^\+7[0-9]{10}$'),
    CONSTRAINT  employee_master_admin_check CHECK   (hotel_id IS NOT NULL OR post LIKE 'MASTER_ADMINISTRATOR')
);

CREATE TABLE IF NOT EXISTS task(
    task_id         BIGSERIAL   PRIMARY KEY,
    employee_id     INTEGER             REFERENCES employee(employee_id),
    hotel_room_id   INTEGER             REFERENCES hotel_room(room_id),
    manager_id      INTEGER     NULL    REFERENCES employee(employee_id),
    status          TEXT        NOT NULL,
    description     TEXT,
    created_at      TIMESTAMP   NOT NULL,
    updated_at      TIMESTAMP   NOT NULL
);