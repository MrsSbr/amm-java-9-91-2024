DROP TABLE IF EXISTS medicine CASCADE;
DROP TABLE IF EXISTS person_entity CASCADE;
DROP TABLE IF EXISTS receipt CASCADE;

CREATE TABLE medicine
(
    id                BIGSERIAL PRIMARY KEY,
    name TEXT NOT NULL,
	description TEXT NOT NULL,
	size TEXT NOT NULL,
	type TEXT NOT NULL
);

CREATE TABLE person_entity
(
    id           BIGSERIAL PRIMARY KEY,
	firstname	 TEXT NOT NULL,
	lastname     TEXT NOT NULL,
	patronymicname TEXT NOT NULL,
    number_phone TEXT NOT NULL UNIQUE,
    email        TEXT NOT NULL UNIQUE
);

CREATE TABLE receipt
(
    id             BIGSERIAL PRIMARY KEY,
	dosage TEXT NOT NULL,
	person_id BIGSERIAL NOT NULL,
	medicine_id BIGSERIAL NOT NULL,
	appointment_time TIME NOT NULL,
	amount_to_consume int NOT NULL,
	amount_consumed int,
    CONSTRAINT fk_person FOREIGN KEY (person_id) REFERENCES person_entity (id),
    CONSTRAINT fk_medicine FOREIGN KEY (medicine_id) REFERENCES medicine (id)
);

CREATE INDEX idx_person_id_receipt ON receipt(person_id);
CREATE INDEX idx_receipt_id_medicine ON receipt(medicine_id);