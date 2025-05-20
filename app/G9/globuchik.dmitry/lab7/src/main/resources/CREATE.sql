--DROP TABLE IF EXISTS achievement CASCADE;
--DROP TABLE IF EXISTS userentity CASCADE;
--DROP TABLE IF EXISTS earnedachievement CASCADE;

CREATE TABLE achievement
(
    id                  BIGSERIAL PRIMARY KEY,
    name                TEXT NOT NULL,
    description         TEXT NOT NULL,
    type                TEXT NOT NULL
);

CREATE TABLE userentity
(
    id              BIGSERIAL PRIMARY KEY,
    login           TEXT NOT NULL UNIQUE,
    nickname        TEXT NOT NULL,
    phonenumber     TEXT NOT NULL UNIQUE,
    passwordhash    TEXT NOT NULL,
    salt            TEXT NOT NULL,
    email           TEXT NOT NULL UNIQUE
);

CREATE TABLE earnedachievement
(
    id              BIGSERIAL PRIMARY KEY,
    achievement_id  BIGINT NOT NULL,
    user_id         BIGINT NOT NULL,
    obtainedat      TIME NOT NULL,
    status          TEXT NOT NULL,
    CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES userentity (id),
    CONSTRAINT fk_achievement FOREIGN KEY (achievement_id) REFERENCES achievement (id)
);

CREATE INDEX idx_user_id_earnedachievement ON earnedachievement(user_id);
CREATE INDEX idx_earnedachievement_id_achievement ON earnedachievement(achievement_id);