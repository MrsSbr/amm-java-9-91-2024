CREATE TABLE guide
(
    id_guide     SERIAL PRIMARY KEY,
    Full_name    VARCHAR(100) NOT NULL,
    Bio          TEXT         NOT NULL,
    Rating       INTEGER      NOT NULL,
    Languages    TEXT	      NOT NULL,
    E_mail       VARCHAR(50)  NOT NULL,
    Number_phone VARCHAR(20)  NOT NULL
);

ALTER TABLE guide
    ADD CONSTRAINT Unique_Guide_E_mail UNIQUE (E_mail);

ALTER TABLE guide
    ADD CONSTRAINT Unique_Guide_Number_phone UNIQUE (Number_phone);

CREATE TABLE tour
(
    id_tour          SERIAL PRIMARY KEY,
    id_guide         INTEGER NOT NULL REFERENCES Guide (id_guide),
    Title            TEXT    NOT NULL,
    Description      TEXT    NOT NULL,
    Duration         INTEGER NOT NULL,
    Price            INTEGER NOT NULL,
    Max_participants INTEGER NOT NULL,
    Start_location   TEXT,
    Language_tour    VARCHAR(200)
);

CREATE TABLE user_tour
(
    id_user      SERIAL PRIMARY KEY,
    Password     VARCHAR(100)  NOT NULL,
    Full_name    VARCHAR(100) NOT NULL,
    Birthday     DATE         NOT NULL,
    E_mail       VARCHAR(50)  NOT NULL,
    Number_phone VARCHAR(20)  NOT NULL,
    User_role    INTEGER       NOT NULL
);

ALTER TABLE user_tour
    ADD CONSTRAINT Unique_User_E_mail UNIQUE (E_mail);

ALTER TABLE user_tour
    ADD CONSTRAINT Unique_User_Number_phone UNIQUE (Number_phone);

CREATE TABLE booking
(
    id_booking          SERIAL PRIMARY KEY,
    id_tour             INTEGER     NOT NULL REFERENCES Tour (id_tour),
    id_user             INTEGER     NOT NULL REFERENCES User_tour (id_user),
    Date_booking        DATE        NOT NULL,
    Count_participation INTEGER     NOT NULL,
    Total_price         INTEGER     NOT NULL,
    Status              VARCHAR(50) NOT NULL
)