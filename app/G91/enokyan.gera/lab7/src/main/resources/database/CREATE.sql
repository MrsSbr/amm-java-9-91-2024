CREATE TABLE user (
    id BIGSERIAL PRIMARY KEY,
    nickname TEXT NOT NULL UNIQUE,
    password TEXT NOT NULL,
    rating DOUBLE PRECISION NOT NULL,
    roles TEXT NOT NULL
);

CREATE TABLE game (
    id BIGSERIAL PRIMARY KEY,
    first_player_id BIGINT NOT NULL,
    second_player_id BIGINT NOT NULL,
    finished TIMESTAMP NOT NULL,
    result TEXT NOT NULL,
    first_players_rating_before DOUBLE PRECISION NOT NULL,
    second_players_rating_before DOUBLE PRECISION NOT NULL,
    CONSTRAINT fk_first_player_id FOREIGN KEY(first_player_id) REFERENCES user(id),
    CONSTRAINT fk_second_player_id FOREIGN KEY(second_player_id) REFERENCES user(id)
);

CREATE INDEX game_fk_first_player_id ON game(first_player_id);
CREATE INDEX game_fk_second_player_id ON game(second_player_id);