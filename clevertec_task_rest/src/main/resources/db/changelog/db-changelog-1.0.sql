--liquibase formatted sql

--changeset grisha:1
CREATE TABLE IF NOT EXISTS shop.product
(
    id bigint,
    created_date timestamp(6) without time zone,
    updated_date timestamp(6) without time zone,
    cost integer,
    count bigint,
    expiration_date timestamp(6) without time zone NOT NULL,
    manufacturer character varying(25),
    name character varying(25),
    weight integer,
    CONSTRAINT product_pkey PRIMARY KEY (id),
    CONSTRAINT product_cost_check CHECK (cost >= 1),
    CONSTRAINT product_weight_check CHECK (weight >= 5)
);

--changeset grisha:2
CREATE TABLE IF NOT EXISTS shop.sale_card
(
    id bigint,
    created_date timestamp(6) without time zone,
    updated_date timestamp(6) without time zone,
    sale_percentage integer,
    year bigint,
    CONSTRAINT sale_card_pkey PRIMARY KEY (id)
);


--changeset grisha:3
CREATE SEQUENCE "PRODUCT_SEQ"
    MINVALUE 9
    MAXVALUE 999999999
    INCREMENT BY 50
    START WITH 202700;





