CREATE TABLE Action
(
    id BIGSERIAL PRIMARY KEY,
    name TEXT UNIQUE,
    points SMALLINT
);

CREATE TABLE Card
(
    id BIGSERIAL PRIMARY KEY,
    topic TEXT NOT NULL UNIQUE,
    difficulty TEXT NOT NULL,
    CONSTRAINT fk_WToA FOREIGN KEY(word_to_action_id) REFERENCES Word_To_Action(id)
);


CREATE TABLE Player
(
    id BIGSERIAL PRIMARY KEY,
    login TEXT NOT NULL UNIQUE,
    password TEXT NOT NULL UNIQUE,
    email TEXT NOT NULL UNIQUE
);

CREATE TABLE Game
(
    id BIGSERIAL PRIMARY KEY,
    card_id BIGINT,
    player_id BIGINT,
    CONSTRAINT fk_card FOREIGN KEY (card_id) REFERENCES Card(id),
    CONSTRAINT fk_player FOREIGN KEY (player_id) REFERENCES Player(id)
);

CREATE TABLE Word_To_Action
(
    id BIGSERIAL PRIMARY KEY,
    word TEXT NOT NULL,
    action_id BIGINT,
    card_id BIGINT,
    CONSTRAINT fk_action FOREIGN KEY (action_id) REFERENCES Action(id)
)