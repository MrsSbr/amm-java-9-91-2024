CREATE TABLE User(
    id BIGSERIAL PRIMARY KEY,
    name TEXT NOT NULL,
    password TEXT NOT NULL
);

CREATE TABLE Scooter (
    id BIGSERIAL PRIMARY KEY,
    model TEXT NOT NULL,
    latitude DOUBLE PRECISION,
    longitude DOUBLE PRECISION,
    status   varchar(10) not null check (status in ('BUSY', 'FREE', 'BROKEN'))
);

CREATE TABLE Trip (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL,
    scooter_id BIGINT NOT NULL,
    start_time TIMESTAMP NOT NULL,
    end_time TIMESTAMP,
    start_latitude DOUBLE PRECISION,
    start_longitude DOUBLE PRECISION,
    end_latitude DOUBLE PRECISION,
    end_longitude DOUBLE PRECISION,
    FOREIGN KEY (user_id) REFERENCES User(id) ON DELETE CASCADE,
    FOREIGN KEY (scooter_id) REFERENCES Scooter(id) ON DELETE SET NULL
);

CREATE INDEX idx_trip_person ON Trip (user_id);

CREATE INDEX idx_trip_scooter ON Trip (scooter_id);
