CREATE TABLE car
(
    id     BIGSERIAL PRIMARY KEY,
    manufacturer TEXT NOT NULL,
    model  TEXT NOT NULL,
    year   INT  NOT NULL,
    status TEXT NOT NULL
);

CREATE TABLE user
(
    id            BIGSERIAL PRIMARY KEY,
    username      TEXT NOT NULL UNIQUE ,
    password TEXT NOT NULL,
    email         TEXT NOT NULL UNIQUE
);

CREATE TABLE user_car
(
    id               BIGSERIAL PRIMARY KEY,
    user_id          BIGINT         NOT NULL,
    car_id           BIGINT         NOT NULL,
    start_trip       TIMESTAMP      NOT NULL,
    end_trip         TIMESTAMP      NOT NULL,
    price_per_minute DECIMAL(10, 2) NOT NULL,
    CONSTRAINT user_car_user_fk FOREIGN KEY (user_id) REFERENCES user (id) ON DELETE CASCADE,
    CONSTRAINT user_car_car_fk FOREIGN KEY (car_id) REFERENCES car(id) ON DELETE CASCADE
);

CREATE INDEX idx_user_car_user_id ON user_car (user_id);
CREATE INDEX idx_user_car_car_id ON user_car (car_id);