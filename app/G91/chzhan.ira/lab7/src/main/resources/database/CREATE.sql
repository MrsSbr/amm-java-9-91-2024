CREATE TABLE customer (
    id BIGSERIAL PRIMARY KEY,
    name TEXT NOT NULL,
    password TEXT NOT NULL
);

CREATE TABLE toy (
    id BIGSERIAL PRIMARY KEY,
    name TEXT NOT NULL,
    price DOUBLE PRECISION NOT NULL
);

CREATE TABLE order (
    id BIGSERIAL PRIMARY KEY,
    customer_id BIGINT NOT NULL,
    toy_id BIGINT NOT NULL,
    quantity INT NOT NULL,
    total_price DOUBLE PRECISION NOT NULL,
    FOREIGN KEY (customer_id) REFERENCES customers(id),
    FOREIGN KEY (toy_id) REFERENCES toys(id)
);

CREATE INDEX idx_orders_customer_id ON orders(customer_id);
CREATE INDEX idx_orders_toy_id ON orders(toy_id);