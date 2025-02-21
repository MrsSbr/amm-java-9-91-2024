CREATE TABLE user_entity (
    id BIGSERIAL PRIMARY KEY,
    login TEXT NOT NULL UNIQUE,
    password TEXT NOT NULL
);
CREATE INDEX user_entity_id_index ON user_entity(id);

CREATE TABLE currency (
    id BIGSERIAL PRIMARY KEY,
    code TEXT UNIQUE NOT NULL,
    name TEXT UNIQUE NOT NULL,
    sign TEXT UNIQUE NOT NULL
);
CREATE INDEX currency_id_index ON currency(id);

CREATE TABLE exchange_rate (
    id BIGSERIAL PRIMARY KEY,
    base_currency_id BIGINT,
    target_currency_id BIGINT,
    rate DOUBLE PRECISION,
    CONSTRAINT fk_base_currency FOREIGN KEY(base_currency_id)
        REFERENCES currency(id),
    CONSTRAINT fk_target_currency FOREIGN KEY(target_currency_id)
        REFERENCES currency(id)
);
CREATE INDEX exchange_rate_id_index ON exchange_rate(id);
CREATE INDEX exchange_rate_fk_base_currency ON exchange_rate(base_currency_id);
CREATE INDEX exchange_rate_fk_target_currency ON exchange_rate(target_currency_id);

