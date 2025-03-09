CREATE TABLE FILM (
                      film_id BIGSERIAL PRIMARY KEY,
                      duration INT NOT NULL,
                      name VARCHAR(255) NOT NULL,
                      screenwriter VARCHAR(255),
                      genre VARCHAR(100),
                      rating DECIMAL(2,1)
);



CREATE TABLE USER (
                      user_id BIGSERIAL PRIMARY KEY,
                      name VARCHAR(255) NOT NULL,
                      email VARCHAR(255) UNIQUE NOT NULL,
                      password_hash VARCHAR(255) NOT NULL,
                      phone VARCHAR(20) UNIQUE NOT NULL
);



CREATE TABLE TICKET (
                        ticket_id BIGSERIAL PRIMARY KEY,
                        film_id BIGINT NOT NULL,
                        user_id BIGINT NOT NULL,
                        hall_num INT NOT NULL,
                        place_num INT NOT NULL,
                        status VARCHAR(50) NOT NULL,
                        start_time TIMESTAMP NOT NULL,
                        end_time TIMESTAMP NOT NULL,
                        FOREIGN KEY (film_id) REFERENCES FILM(film_id) ON DELETE CASCADE,
                        FOREIGN KEY (user_id) REFERENCES USER(user_id) ON DELETE CASCADE
);