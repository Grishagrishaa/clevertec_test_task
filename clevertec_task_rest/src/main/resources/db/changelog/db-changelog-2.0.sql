--liquibase formatted sql

-- changeset grisha:1:insertSQLChangeType
INSERT INTO shop.product (id, created_date, updated_date, cost, count, expiration_date, manufacturer, name, weight)
VALUES (1000, '2022-12-21 18:56:01.000000', '2022-12-21 18:56:02.000000', 8000.20, 8, '2022-12-21 18:56:02.000000', 'Apple',
        'IPhone 11 Pro', 500);
INSERT INTO shop.product (id, created_date, updated_date, cost, count, expiration_date, manufacturer, name, weight)
VALUES (1002, '2022-12-21 18:56:01.000000', '2022-12-21 18:56:02.000000', 9000.10, 7, '2022-12-21 18:56:02.000000', 'Apple',
        'IPhone 12 Pro', 500);
INSERT INTO shop.product (id, created_date, updated_date, cost, count, expiration_date, manufacturer, name, weight)
VALUES (1003, '2022-12-21 18:56:01.000000', '2022-12-21 18:56:02.000000', 10000.99, 4, '2022-12-21 18:56:02.000000', 'Apple',
        'IPhone 13 Pro', 500);
INSERT INTO shop.product (id, created_date, updated_date, cost, count, expiration_date, manufacturer, name, weight)
VALUES (1004, '2022-12-21 18:56:01.000000', '2022-12-21 18:56:02.000000', 7000.54, 9, '2022-12-21 18:56:02.000000', 'Apple',
        'IPhone Xs', 500);
INSERT INTO shop.product (id, created_date, updated_date, cost, count, expiration_date, manufacturer, name, weight)
VALUES (1005, '2022-12-21 18:56:01.000000', '2022-12-21 18:56:02.000000', 6000.45, 1, '2022-12-21 18:56:02.000000', 'Apple',
        'IPhone X', 500);
INSERT INTO shop.product (id, created_date, updated_date, cost, count, expiration_date, manufacturer, name, weight)
VALUES (1006, '2022-12-21 18:56:01.000000', '2022-12-21 18:56:02.000000', 5000.00, 5, '2022-12-21 18:56:02.000000', 'Apple',
        'IPhone 8', 500);
INSERT INTO shop.product (id, created_date, updated_date, cost, count, expiration_date, manufacturer, name, weight)
VALUES (1007, '2022-12-21 18:56:01.000000', '2022-12-21 18:56:02.000000', 4000.98, 8, '2022-12-21 18:56:02.000000', 'Apple',
        'IPhone 7', 500);
INSERT INTO shop.product (id, created_date, updated_date, cost, count, expiration_date, manufacturer, name, weight)
VALUES (1008, '2022-12-21 18:56:01.000000', '2022-12-21 18:56:02.000000', 3000.21, 8, '2022-12-21 18:56:02.000000', 'Apple',
        'IPhone 6', 500);
INSERT INTO shop.product (id, created_date, updated_date, cost, count, expiration_date, manufacturer, name, weight)
VALUES (1009, '2022-12-21 18:56:01.000000', '2022-12-21 18:56:02.000000', 2000.34, 90, '2022-12-21 18:56:02.000000', 'Apple',
        'IPhone 5S', 500);
INSERT INTO shop.product (id, created_date, updated_date, cost, count, expiration_date, manufacturer, name, weight)
VALUES (1010, '2022-12-21 18:56:01.000000', '2022-12-21 18:56:02.000000', 1000.76, 3, '2022-12-21 18:56:02.000000', 'Apple',
        'IPhone 4S', 500);



