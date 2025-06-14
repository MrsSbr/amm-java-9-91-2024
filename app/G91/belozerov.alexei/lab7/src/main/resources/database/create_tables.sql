CREATE TABLE IF NOT EXISTS client (
    client_id BIGSERIAL PRIMARY KEY,
    name TEXT NOT NULL,
    email TEXT UNIQUE NOT NULL,
    password TEXT NOT NULL
);

CREATE TABLE IF NOT EXISTS flight (
  flight_id BIGSERIAL PRIMARY KEY,
  origin TEXT NOT NULL,
  destination TEXT NOT NULL,
  departure_time TIMESTAMP NOT NULL,
  arrival_time TIMESTAMP NOT NULL,
  airplane_model TEXT,
  capacity SMALLINT NOT NULL CHECK (capacity > 0),
  price DECIMAL(10,2) NOT NULL CHECK (price >= 0)
);

CREATE TABLE IF NOT EXISTS booking (
    booking_id BIGSERIAL PRIMARY KEY,
    client_id BIGINT NOT NULL REFERENCES client (client_id) ON DELETE CASCADE,
    flight_id BIGINT NOT NULL REFERENCES flight (flight_id) ON DELETE CASCADE,
    ticket_number TEXT NOT NULL UNIQUE,
    seat_number TEXT NOT NULL,

    CONSTRAINT uq_flight_seat UNIQUE (flight_id, seat_number)
);

CREATE INDEX idx_booking_client_id ON booking (client_id);
CREATE INDEX idx_booking_flight_id ON booking (flight_id);
CREATE INDEX idx_flight_depart_time ON flight (departure_time);
