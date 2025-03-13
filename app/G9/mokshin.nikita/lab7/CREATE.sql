CREATE TABLE user_entity (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100),
    login VARCHAR(100) UNIQUE,
    hash_password VARCHAR
);

CREATE TABLE genre (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(100) UNIQUE
);

CREATE TABLE film (
    id BIGSERIAL PRIMARY KEY,
    title TEXT,
    slogan TEXT,
    description TEXT,
    release_date DATE,
    id_genre BIGINT,
    id_user BIGINT,
    CONSTRAINT fk_genre FOREIGN KEY(id_genre)
        REFERENCES genre(id),
    CONSTRAINT fk_user FOREIGN KEY(id_user)
        REFERENCES user_entity(id)
);

CREATE INDEX fk_genre_index ON film(id_genre);
CREATE INDEX fk_user_index ON film(id_user);
CREATE INDEX login_index ON user_entity(login);