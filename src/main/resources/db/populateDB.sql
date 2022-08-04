DELETE FROM users;
DELETE FROM merch;
DELETE FROM orders;
DELETE FROM order_merch;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password)
VALUES ('User', 'user@yandex.ru', 'password'),
       ('Admin', 'admin@gmail.com', 'admin'),
       ('Guest', 'guest@gmail.com', 'guest');

INSERT INTO user_roles (role, user_id)
VALUES ('USER', 100000),
       ('ADMIN', 100001),
       ('USER', 100001);

INSERT INTO merch (name, curr_price)
VALUES ('Merch 1', 100),
       ('Merch 2', 500);

INSERT INTO orders (user_id, date_time, status)
VALUES (100000, '2022-03-1 1:11:00', 'DRAFT'),
       (100000, '2022-04-2 3:33:00', 'COMPLETED'),
       (100000, '2022-05-3 4:44:00', 'DELETED');

INSERT INTO order_merch (order_id, merch_id, price, count)
VALUES (100005, 100003, 100, 2),
       (100005, 100004, 500, 5);

