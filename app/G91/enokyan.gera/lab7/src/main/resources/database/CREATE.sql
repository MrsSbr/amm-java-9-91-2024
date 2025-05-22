CREATE TABLE elo_user (
    id BIGSERIAL PRIMARY KEY,
    nickname TEXT NOT NULL UNIQUE,
    password TEXT NOT NULL,
    rating DOUBLE PRECISION NOT NULL,
    roles TEXT[] NOT NULL
);

CREATE INDEX user_rating ON elo_user(rating);

CREATE TABLE game (
    id BIGSERIAL PRIMARY KEY,
    first_players_id BIGINT,
    second_players_id BIGINT,
    finished TIMESTAMP NOT NULL,
    result TEXT NOT NULL,
    first_players_rating_before DOUBLE PRECISION NOT NULL,
    second_players_rating_before DOUBLE PRECISION NOT NULL,
    first_players_rating_change DOUBLE PRECISION NOT NULL,
    second_players_rating_change DOUBLE PRECISION NOT NULL,
    CONSTRAINT fk_first_players_id FOREIGN KEY(first_players_id) REFERENCES elo_user(id) ON DELETE SET NULL,
    CONSTRAINT fk_second_players_id FOREIGN KEY(second_players_id) REFERENCES elo_user(id) ON DELETE SET NULL
);

CREATE INDEX game_fk_first_players_id ON game(first_players_id);
CREATE INDEX game_fk_second_players_id ON game(second_players_id);
CREATE INDEX game_finished ON game(finished);