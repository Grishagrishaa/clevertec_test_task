
--1. Вывести к каждому самолету класс обслуживания и количество мест этого класса
SELECT aircrafts_data.aircraft_code, seats.fare_conditions,
       aircrafts_data.model::json ->> 'ru' AS model,
       count(seats.seat_no)
FROM bookings.aircrafts_data JOIN bookings.seats ON aircrafts_data.aircraft_code = seats.aircraft_code
GROUP BY aircrafts_data.model, aircrafts_data.aircraft_code, seats.fare_conditions
ORDER BY aircrafts_data.aircraft_code;
--2.  Найти 3 самых вместительных самолета (модель + кол-во мест)
SELECT aircrafts_data.model::json ->> 'ru' AS model, count(seats.seat_no) AS n_seats
FROM bookings.seats JOIN bookings.aircrafts_data ON aircrafts_data.aircraft_code = seats.aircraft_code
GROUP BY aircrafts_data.model
ORDER BY n_seats DESC
LIMIT 3;
--3. Вывести код,модель самолета и места не эконом класса для самолета 'Аэробус A321-200' с сортировкой по местам
SELECT aircrafts_data.aircraft_code, seat_no,  model:: json ->> 'ru' AS Model
FROM bookings.aircrafts_data JOIN bookings.seats ON aircrafts_data.aircraft_code = seats.aircraft_code
WHERE model:: json ->> 'ru' = 'Аэробус A321-200' AND seats.fare_conditions != 'Economy'
ORDER BY seat_no;
--4. Вывести города в которых больше 1 аэропорта ( код аэропорта, аэропорт, город)
SELECT airport_code, city, airport_name
FROM bookings.airports_data
WHERE city =
      (SELECT city
       FROM airports_data
       GROUP BY city
       having count(city) > 2);
--5.  Найти ближайший вылетающий рейс из Екатеринбурга в Москву, на который еще не завершилась регистрация
SELECT flight_id, flight_no, scheduled_departure, scheduled_arrival, departure_airport,
       arrival_airport, status,  aircraft_code, actual_departure, actual_arrival
FROM bookings.flights
WHERE bookings.flights.departure_airport IN (
                SELECT ad.airport_code
                FROM bookings.airports_data ad
                WHERE ad.city @> '{"ru":"Екатеринбург"}')
  AND bookings.flights.arrival_airport IN (
                SELECT ad.airport_code
                FROM bookings.airports_data ad
                WHERE ad.city @> '{"ru":"Москва"}')
  AND (bookings.flights.status LIKE 'Scheduled' OR bookings.flights.status LIKE 'Delayed')
ORDER BY bookings.flights.scheduled_departure
LIMIT 1;
--Вывести самый дешевый и дорогой билет и стоимость ( в одном результирующем ответе)

SELECT amount, ticket_no AS ticket_number
FROM bookings.ticket_flights
WHERE amount IN
      ((SELECT MIN(amount) FROM ticket_flights ),
       (SELECT MAX(amount) FROM ticket_flights ));
-- Написать DDL таблицы Customers , должны быть поля id , firstName, LastName, email , phone. Добавить ограничения на поля ( constraints) .
CREATE TABLE IF NOT EXISTS bookings.customers(
    id bigserial NOT NULL,
    first_name character varying(30) NOT NULL,
    last_name character varying(30) NOT NULL,
    email character varying(40) NOT NULL,
    phone character varying(15) NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT email_constraint UNIQUE (phone),
    CONSTRAINT phone_constraint UNIQUE (phone)
                               );
-- Написать DDL таблицы Orders , должен быть id, customerId, quantity. Должен быть внешний ключ на таблицу customers + ограничения
CREATE TABLE IF NOT EXISTS bookings.orders(
    id bigserial NOT NULL,
    customer_id bigint NOT NULL,
    quantity bigint NOT NULL,
    CONSTRAINT orders_pkey PRIMARY KEY (id),
    CONSTRAINT customer_id FOREIGN KEY (customer_id)
        REFERENCES bookings.customers (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
                                      );
-- Написать 5 insert в эти таблицы
INSERT INTO bookings.customers (first_name, last_name, email, phone)
VALUES ('Grisha', 'Mitskevich', 'grisha.private@gmail.com', '+375299802357'),
       ('Vlad', 'Brel', 'vlad.brel@gmail.com', '+375291234567'),
       ('Tsimafey', 'Labanovich', 'tsimafey.labanovich@gmail.com', '+375281234567'),
       ('Andrey', 'Shipul', 'andrey.shipul@gmail.com', '+375271234567'),
       ('Alexey', 'Pavluchenkov', 'alexey.pavluchenkov@gmail.com', '+375261234567');

INSERT INTO bookings.orders(quantity, customer_id)
VALUES (1000, 1),
       (12024, 2),
       (323234, 3),
       (62352, 4),
       (5638, 5);
-- удалить таблицы

DROP TABLE IF EXISTS customers CASCADE;
DROP TABLE IF EXISTS orders;

-- Написать свой кастомный запрос ( rus + sql)
-- Вывeсти код самолета, модель, дальность
-- где дальность между минимальной и средней и код самолета содержит C
SELECT aircraft_code AS code, model::json ->> 'ru' AS model, range as range
FROM bookings.aircrafts_data
WHERE range BETWEEN
    (SELECT MIN(range) FROM bookings.aircrafts_data) AND
    (SELECT avg(range) FROM bookings.aircrafts_data)
AND aircraft_code LIKE 'C%'