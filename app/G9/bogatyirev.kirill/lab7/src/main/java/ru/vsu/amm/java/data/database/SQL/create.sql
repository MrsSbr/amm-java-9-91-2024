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
    player_id BIGINT,
    CONSTRAINT fk_Player FOREIGN KEY(player_id) REFERENCES Player(id)
);


CREATE TABLE Player
(
    id BIGSERIAL PRIMARY KEY,
    login TEXT NOT NULL UNIQUE,
    password TEXT NOT NULL UNIQUE,
    email TEXT NOT NULL UNIQUE
);

CREATE TABLE Word_To_Action
(
    id BIGSERIAL PRIMARY KEY,
    word TEXT NOT NULL,
    action_id BIGINT,
    card_id BIGINT,
    CONSTRAINT fk_action FOREIGN KEY (action_id) REFERENCES Action(id)
)