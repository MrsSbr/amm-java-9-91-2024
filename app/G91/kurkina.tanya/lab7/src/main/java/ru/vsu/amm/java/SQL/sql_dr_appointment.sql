CREATE TABLE Doctor (
    Id_doctor SERIAL PRIMARY KEY,
    Full_name VARCHAR(100) NOT NULL,
    Speciality VARCHAR(100) NOT NULL
);

CREATE TABLE Patient (
    Id_patient SERIAL PRIMARY KEY,
    Full_name VARCHAR(100) NOT NULL,
    Birth_date DATE NOT NULL,
    Phone_number VARCHAR(12) NOT NULL,
    Email VARCHAR(50) NOT NULL,
    Password VARCHAR(50) NOT NULL
);

CREATE TABLE Appointment (
    Id_appointment SERIAL PRIMARY KEY,
    FOREIGN KEY (Id_doctor) REFERENCES Doctor(Id_doctor) ON DELETE SET NULL,
    FOREIGN KEY (Id_patient) REFERENCES Patient(Id_patient) ON DELETE SET NULL,
    Date_time TIMESTAMP NOT NULL,
    Hospital_address VARCHAR(200) NOT NULL,
    Office_number VARCHAR(20) NOT NULL,
    Price DECIMAL(10,2),
    Status VARCHAR(20) NOT NULL
);