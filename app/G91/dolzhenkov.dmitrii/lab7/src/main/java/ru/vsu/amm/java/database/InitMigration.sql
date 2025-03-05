CREATE TABLE Person (
    id BIGSERIAL PRIMARY KEY,
    name TEXT NOT NULL,
    hash_password TEXT NOT NULL
);

CREATE TABLE Scooter (
    id BIGSERIAL PRIMARY KEY,
    model TEXT NOT NULL,
    location TEXT
);

CREATE TABLE Trip (
    id BIGSERIAL PRIMARY KEY,
    person_id BIGINT NOT NULL,
    scooter_id BIGINT NOT NULL,
    start_time TIMESTAMP NOT NULL,
    end_time TIMESTAMP,
    startLocation TEXT,
    endLocation TEXT,
    FOREIGN KEY (person_id) REFERENCES Person(id) ON DELETE CASCADE,
    FOREIGN KEY (scooter_id) REFERENCES Scooter(id) ON DELETE SET NULL
);

CREATE INDEX idx_trip_person ON Trip (person_id);

CREATE INDEX idx_trip_scooter ON Trip (scooter_id);
