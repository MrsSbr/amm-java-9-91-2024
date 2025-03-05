CREATE TABLE car
(
    id     BIGSERIAL PRIMARY KEY,
    make   TEXT NOT NULL,
    model  TEXT NOT NULL,
    year   INT  NOT NULL,
    status TEXT NOT NULL
);

CREATE INDEX idx_car_id ON car (id);

CREATE TABLE users
(
    id            BIGSERIAL PRIMARY KEY,
    username      TEXT NOT NULL,
    hash_password TEXT NOT NULL,
    email         TEXT NOT NULL
);

CREATE INDEX idx_users_id ON users (id);

ALTER TABLE users
    ADD CONSTRAINT users_username_unique UNIQUE (username),
    ADD CONSTRAINT users_email_unique UNIQUE (email);

CREATE TABLE user_car
(
    id               BIGSERIAL PRIMARY KEY,
    user_id          BIGINT         NOT NULL,
    car_id           BIGINT         NOT NULL,
    start_trip       TIMESTAMP      NOT NULL,
    duration         INTEGER,
    price_per_minute DECIMAL(10, 2) NOT NULL
);

CREATE INDEX idx_user_car_id ON user_car (id);
CREATE INDEX idx_user_car_user_id ON user_car (user_id);
CREATE INDEX idx_user_car_car_id ON user_car (car_id);

ALTER TABLE user_car
    ADD CONSTRAINT user_car_user_fk FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
    ADD CONSTRAINT user_car_car_fk FOREIGN KEY (car_id) REFERENCES car(id) ON DELETE CASCADE;