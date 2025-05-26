CREATE TABLE real_estate
(
    id                       BIGSERIAL PRIMARY KEY,
    address                  TEXT NOT NULL UNIQUE,
    type                     TEXT NOT NULL,
    maximum_number_of_guests INT  NOT NULL,
    rules                    TEXT NOT NULL,
    image_name               TEXT,
    price                    BIGINT
);

CREATE TABLE user_entity
(
    id           BIGSERIAL PRIMARY KEY,
    login        TEXT NOT NULL UNIQUE,
    password     TEXT NOT NULL,
    number_phone TEXT NOT NULL UNIQUE,
    email        TEXT NOT NULL UNIQUE
);

CREATE TABLE booking
(
    id             BIGSERIAL PRIMARY KEY,
    check_in_date  DATE    NOT NULL,
    check_out_date DATE    NOT NULL,
    status         TEXT NOT NULL,
    user_id        BIGINT,
    real_estate_id BIGINT,
    CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES user_entity (id),
    CONSTRAINT fk_real_estate FOREIGN KEY (real_estate_id) REFERENCES real_estate (id)
);

CREATE INDEX idx_user_id_booking ON booking(user_id);
CREATE INDEX idx_real_estate_id_real_estate ON booking(real_estate_id);