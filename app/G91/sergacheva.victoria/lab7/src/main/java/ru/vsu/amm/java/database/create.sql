CREATE TABLE user_entity (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    login VARCHAR(100) UNIQUE NOT NULL,
    hash_password TEXT NOT NULL
);

CREATE TABLE coffee (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(100) NOT NULL,
    description TEXT,
    id_author BIGINT NOT NULL,
    CONSTRAINT fk_coffee_author FOREIGN KEY (id_author) REFERENCES user_entity(id) ON DELETE CASCADE
);

CREATE TABLE user_coffee (
    id_coffee BIGINT NOT NULL,
    id_user BIGINT NOT NULL,
    PRIMARY KEY (id_coffee, id_user),
    CONSTRAINT fk_user_coffee_coffee FOREIGN KEY (id_coffee) REFERENCES coffee(id) ON DELETE CASCADE,
    CONSTRAINT fk_user_coffee_user FOREIGN KEY (id_user) REFERENCES user_entity(id) ON DELETE CASCADE
);

CREATE INDEX fk_author_index ON coffee(id_author);
CREATE INDEX fk_coffee_index ON user_coffee(id_coffee);
CREATE INDEX fk_user_index ON user_coffee(id_user);
CREATE INDEX login_index ON user_entity(login);
