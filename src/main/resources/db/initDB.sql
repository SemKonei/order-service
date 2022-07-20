DROP TABLE IF EXISTS order_merch;
DROP TABLE IF EXISTS orders;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS merch;
DROP SEQUENCE IF EXISTS global_seq;

CREATE SEQUENCE global_seq START WITH 100000;

CREATE TABLE users
(
    id         BIGINT    DEFAULT NEXT VALUE FOR global_seq PRIMARY KEY,
    name       VARCHAR                 NOT NULL,
    email      VARCHAR                 NOT NULL,
    password   VARCHAR                 NOT NULL,
    registered TIMESTAMP DEFAULT now() NOT NULL,
    enabled    BOOLEAN      DEFAULT TRUE  NOT NULL
);

CREATE TABLE merch
(
    id         BIGINT DEFAULT NEXT VALUE FOR global_seq PRIMARY KEY,
    name       VARCHAR NOT NULL,
    curr_price FLOAT   NOT NULL CHECK (curr_price > 0)
);

CREATE TABLE orders
(
    id        BIGINT    DEFAULT NEXT VALUE FOR global_seq PRIMARY KEY,
    user_id   INTEGER                 NOT NULL,
    date_time TIMESTAMP DEFAULT now() NOT NULL,
    status    BOOLEAN   DEFAULT FALSE NOT NULL,
    /*price    FLOAT   NOT NULL,*/
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);

CREATE TABLE order_merch
(
    id       BIGINT DEFAULT NEXT VALUE FOR global_seq PRIMARY KEY,
    order_id INTEGER NOT NULL,
    merch_id INTEGER NOT NULL,
    price    FLOAT   NOT NULL CHECK (price > 0),
    count    INTEGER NOT NULL CHECK (count > 0),
    FOREIGN KEY (order_id) REFERENCES orders (id) ON DELETE CASCADE,
    FOREIGN KEY (merch_id) REFERENCES merch (id) ON DELETE CASCADE
);
CREATE UNIQUE INDEX order_merch_unique_idx ON order_merch (order_id, merch_id);