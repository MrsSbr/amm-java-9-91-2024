DROP TABLE IF EXISTS achievement CASCADE;
DROP TABLE IF EXISTS userentity CASCADE;
DROP TABLE IF EXISTS earnedachievement CASCADE;

CREATE TABLE achievement
(
    id                  BIGSERIAL PRIMARY KEY,
    name                TEXT UNIQUE NOT NULL,
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
    CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES userentity (id),
    CONSTRAINT fk_achievement FOREIGN KEY (achievement_id) REFERENCES achievement (id)
);

INSERT INTO achievement (name, description, type)
VALUES ('test', 'test_desc', 'COMMON');

INSERT INTO achievement (name, description, type)
VALUES
    ('Регистрация', 'Добро пожаловать! Вы зарегистрировались.', 'REGISTRATION'),
    ('Активный пользователь', 'Вошел в систему 5 раз.', 'LOGIN_COUNT');


CREATE INDEX idx_user_id_earnedachievement ON earnedachievement(user_id);
CREATE INDEX idx_earnedachievement_id_achievement ON earnedachievement(achievement_id);

SELECT * FROM userentity;

TRUNCATE TABLE userentity CASCADE ;
TRUNCATE TABLE achievement CASCADE ;

SELECT * FROM achievement;
SELECT * FROM earnedachievement;