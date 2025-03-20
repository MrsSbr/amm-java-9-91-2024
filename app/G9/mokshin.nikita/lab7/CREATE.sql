CREATE TABLE user_entity (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100),
    login VARCHAR(100) UNIQUE NOT NULL,
    hash_password VARCHAR NOT NULL
);

CREATE TABLE genre (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(100) UNIQUE NOT NULL
);

CREATE TABLE film (
    id BIGSERIAL PRIMARY KEY,
    title TEXT NOT NULL,
    slogan TEXT,
    description TEXT,
    release_date DATE,
    id_genre BIGINT NOT NULL,
    id_user BIGINT NOT NULL,
    CONSTRAINT fk_genre FOREIGN KEY(id_genre)
        REFERENCES genre(id) ON DELETE CASCADE,
    CONSTRAINT fk_user FOREIGN KEY(id_user)
        REFERENCES user_entity(id) ON DELETE CASCADE
);

CREATE INDEX fk_genre_index ON film(id_genre);
CREATE INDEX fk_user_index ON film(id_user);
CREATE INDEX login_index ON user_entity(login);