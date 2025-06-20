DROP TABLE IF EXISTS achievement CASCADE;
DROP TABLE IF EXISTS userentity CASCADE;
DROP TABLE IF EXISTS earnedachievement CASCADE;

CREATE TABLE achievement
(
    id                  BIGSERIAL PRIMARY KEY,
    name                TEXT NOT NULL,
    description         TEXT NOT NULL,
    type                TEXT UNIQUE NOT NULL,
    required_progress   INT NOT NULL
);

SELECT * FROM achievement;

CREATE TABLE userentity
(
    id              BIGSERIAL PRIMARY KEY,
    login           TEXT NOT NULL UNIQUE,
    nickname        TEXT NOT NULL,
    phonenumber     TEXT NOT NULL UNIQUE,
    passwordhash    TEXT NOT NULL,
    salt            TEXT NOT NULL,
    email           TEXT NOT NULL UNIQUE,
    login_count     INT NOT NULL DEFAULT 0
);

CREATE TABLE earnedachievement
(
    id              BIGSERIAL PRIMARY KEY,
    achievement_id  BIGINT NOT NULL,
    user_id         BIGINT NOT NULL,
    obtainedat      TIME,
    status          TEXT NOT NULL,
    progress        INT DEFAULT 0,
    CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES userentity (id),
    CONSTRAINT fk_achievement FOREIGN KEY (achievement_id) REFERENCES achievement (id)
);

INSERT INTO achievement (name, description, type, required_progress)
VALUES ('test', 'test_desc', 'COMMON', 5);

INSERT INTO achievement (name, description, type, required_progress)
VALUES
    ('Регистрация', 'Добро пожаловать! Вы зарегистрировались.', 'REGISTRATION', 1),
    ('Активный пользователь', 'Вошел в систему 5 раз.', 'LOGIN_COUNT', 5);


CREATE INDEX idx_user_id_earnedachievement ON earnedachievement(user_id);
CREATE INDEX idx_earnedachievement_id_achievement ON earnedachievement(achievement_id);

SELECT * FROM userentity;

TRUNCATE TABLE userentity CASCADE ;

TRUNCATE TABLE achievement CASCADE ;

SELECT * FROM achievement;
SELECT * FROM earnedachievement;

SELECT * FROM achievement WHERE type = ('LOGIN_COUNT');