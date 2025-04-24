CREATE TABLE Hotel (
    HotelID SERIAL,
    HotelName VARCHAR(100) NOT NULL,
    Address VARCHAR(200) NOT NULL,
    City VARCHAR(50) NOT NULL,
    Country VARCHAR(50) NOT NULL,
    Rating DECIMAL(2,1),
    Description TEXT
);

CREATE TABLE Users (
    UserID SERIAL,
    Username VARCHAR(50) NOT NULL,
    UserPassword VARCHAR(100) NOT NULL,
    Email VARCHAR(100) NOT NULL,
    FirstName VARCHAR(50) NOT NULL,
    LastName VARCHAR(50) NOT NULL,
    UserRole VARCHAR(20) NOT NULL
);

CREATE TABLE Room (
    RoomID SERIAL,
    HotelID INTEGER NOT NULL,
    RoomType VARCHAR(20) NOT NULL,
    PricePerNight DECIMAL(10,2) NOT NULL,
    Capacity INTEGER NOT NULL,
    Description TEXT
);

CREATE TABLE Booking (
    BookingID SERIAL,
    UserID INTEGER NOT NULL,
    RoomID INTEGER NOT NULL,
    CheckInDate DATE NOT NULL,
    CheckOutDate DATE NOT NULL,
    NumberOfGuests INTEGER NOT NULL,
    TotalAmount DECIMAL(10,2) NOT NULL,
    BookingStatus VARCHAR(20) NOT NULL
);

-- Добавление первичных ключей через ALTER TABLE
ALTER TABLE Hotel ADD CONSTRAINT pk_hotel PRIMARY KEY (HotelID);
ALTER TABLE Users ADD CONSTRAINT pk_user PRIMARY KEY (UserID);
ALTER TABLE Room ADD CONSTRAINT pk_room PRIMARY KEY (RoomID);
ALTER TABLE Booking ADD CONSTRAINT pk_booking PRIMARY KEY (BookingID);

-- Добавление внешних ключей через ALTER TABLE
ALTER TABLE Room ADD CONSTRAINT fk_room_hotel 
    FOREIGN KEY (HotelID) REFERENCES Hotel(HotelID) ON DELETE CASCADE;
    
ALTER TABLE Booking ADD CONSTRAINT fk_booking_user 
    FOREIGN KEY (UserID) REFERENCES Users(UserID) ON DELETE CASCADE;
    
ALTER TABLE Booking ADD CONSTRAINT fk_booking_room 
    FOREIGN KEY (RoomID) REFERENCES Room(RoomID) ON DELETE CASCADE;

-- Добавление ограничений CHECK через ALTER TABLE
ALTER TABLE Hotel ADD CONSTRAINT chk_hotel_rating 
    CHECK (Rating BETWEEN 1 AND 5);
    
ALTER TABLE Users ADD CONSTRAINT chk_user_role 
    CHECK (UserRole IN ('Admin', 'Customer'));
    
ALTER TABLE Users ADD CONSTRAINT unq_user_username 
    UNIQUE (Username);
    
ALTER TABLE Users ADD CONSTRAINT unq_user_email 
    UNIQUE (Email);
    
ALTER TABLE Room ADD CONSTRAINT chk_room_type 
    CHECK (RoomType IN ('Single', 'Double', 'Suite', 'Deluxe', 'Family'));
    
ALTER TABLE Room ADD CONSTRAINT chk_room_price 
    CHECK (PricePerNight > 0);
    
ALTER TABLE Room ADD CONSTRAINT chk_room_capacity 
    CHECK (Capacity > 0);
    
ALTER TABLE Booking ADD CONSTRAINT chk_booking_status 
    CHECK (BookingStatus IN ('Confirmed', 'Cancelled', 'Pending'));
    
ALTER TABLE Booking ADD CONSTRAINT chk_booking_guests 
    CHECK (NumberOfGuests > 0);
    
ALTER TABLE Booking ADD CONSTRAINT chk_booking_amount 
    CHECK (TotalAmount > 0);
    
ALTER TABLE Booking ADD CONSTRAINT chk_booking_dates 
    CHECK (CheckOutDate > CheckInDate);

-- Добавление ограничения для пароля (минимум 8 символов)
ALTER TABLE Users ADD CONSTRAINT chk_password_length 
    CHECK (LENGTH(UserPassword) >= 8);

-- Добавление ограничения сложности пароля (минимум 1 цифра, 1 буква в верхнем регистре, 1 буква в нижнем регистре)
ALTER TABLE Users ADD CONSTRAINT chk_password_complexity
    CHECK (UserPassword ~ '[0-9]' AND 
           UserPassword ~ '[A-Z]' AND 
           UserPassword ~ '[a-z]');

-- Добавление ограничения уникальности 
ALTER TABLE Users ADD CONSTRAINT unq_user_name 
    UNIQUE (Email);

