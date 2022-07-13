DELETE FROM users;
DELETE FROM merch;
DELETE FROM orders;
DELETE FROM order_merch;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password)
VALUES ('User', 'user@yandex.ru', 'password'),
       ('Admin', 'admin@gmail.com', 'admin'),
       ('Guest', 'guest@gmail.com', 'guest');

INSERT INTO merch (name, curr_price)
VALUES ('Merch 1', 100),
       ('Merch 2', 500);

INSERT INTO orders (user_id, date_time)
VALUES (100000, '2022-03-1 1:11:00'),
       (100000, '2022-04-2 3:33:00'),
       (100000, '2022-05-3 4:44:00');

INSERT INTO order_merch (order_id, merch_id, price, count)
VALUES (100005, 100003, 100, 2),
       (100005, 100004, 500, 5);

