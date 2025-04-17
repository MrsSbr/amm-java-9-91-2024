CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    email TEXT UNIQUE NOT NULL,
    hash_password TEXT NOT NULL
);


CREATE TABLE transactions (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT REFERENCES users(id),
    amount NUMERIC(19, 2) NOT NULL,
    type BOOLEAN NOT NULL,
    date_create TIMESTAMP NOT NULL,
    category TEXT NOT NULL
);

CREATE INDEX idx_on_user_id_fk ON transactions(user_id);