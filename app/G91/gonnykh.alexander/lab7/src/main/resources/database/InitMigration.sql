CREATE TABLE car
(
    id     BIGSERIAL PRIMARY KEY,
    make   TEXT NOT NULL,
    model  TEXT NOT NULL,
    year   INT  NOT NULL,
    status TEXT NOT NULL
);

ALTER TABLE car
    ADD CONSTRAINT car_status_check CHECK (status IN ('AVAILABLE', 'IN_USE', 'MAINTENANCE'));

CREATE TABLE users
(
    id            BIGSERIAL PRIMARY KEY,
    username      TEXT NOT NULL,
    hash_password TEXT NOT NULL,
    email         TEXT NOT NULL
);

ALTER TABLE users
    ADD CONSTRAINT users_username_unique UNIQUE (username),
    ADD CONSTRAINT users_email_unique UNIQUE (email);

CREATE TABLE user_car
(
    id               BIGSERIAL PRIMARY KEY,
    user_id          BIGINT         NOT NULL,
    car_id           BIGINT         NOT NULL,
    start_trip       TIMESTAMP      NOT NULL,
    duration         BIGINT,
    price_per_minute DECIMAL(10, 2) NOT NULL
);

ALTER TABLE user_car
    ADD CONSTRAINT user_car_user_fk FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
    ADD CONSTRAINT user_car_car_fk FOREIGN KEY (car_id) REFERENCES car(id) ON DELETE CASCADE,
    ADD CONSTRAINT user_car_duration_check CHECK (duration >= 0),
    ADD CONSTRAINT user_car_price_check CHECK (price_per_minute >= 0);

CREATE INDEX idx_user_car_user_id ON user_car (user_id);
CREATE INDEX idx_user_car_car_id ON user_car (car_id);