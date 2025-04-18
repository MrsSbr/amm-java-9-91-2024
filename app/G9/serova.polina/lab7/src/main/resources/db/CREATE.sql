CREATE TABLE IF NOT EXISTS user_entity (
    id SERIAL PRIMARY KEY,
    name TEXT NOT NULL,
    surname TEXT NOT NULL,
    patronymic TEXT,
    birthday DATE,
    email TEXT NOT NULL,
    password_hash TEXT NOT NULL,
    role TEXT NOT NULL
);
CREATE INDEX IF NOT EXISTS user_entity_email ON user_entity(email);

CREATE TABLE IF NOT EXISTS plan_entity (
    id SERIAL PRIMARY KEY,
    medication_name TEXT NOT NULL,
    dosage_mg DOUBLE PRECISION NOT NULL,
    taking_time TIME[] NOT NULL,
    duration_days INTEGER NOT NULL,
    id_doctor INT NOT NULL,
    id_patient INT NOT NULL,
    CONSTRAINT fk_doctor FOREIGN KEY(id_doctor)
        REFERENCES user_entity(id),
    CONSTRAINT fk_patient FOREIGN KEY(id_patient)
            REFERENCES user_entity(id)
);
CREATE INDEX IF NOT EXISTS plan_entity_fk_doctor ON plan_entity(id_doctor);
CREATE INDEX IF NOT EXISTS plan_entity_fk_patient ON plan_entity(id_patient);