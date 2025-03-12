CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    email VARCHAR(255) UNIQUE NOT NULL,
    hash_password VARCHAR(255) NOT NULL
);


CREATE TABLE transactions (
    id SERIAL PRIMARY KEY,
    user_id BIGINT REFERENCES users(id),
    amount INTEGER NOT NULL,
    type BOOLEAN NOT NULL,
    data TIMESTAMP NOT NULL,
    category VARCHAR(50) NOT NULL
);