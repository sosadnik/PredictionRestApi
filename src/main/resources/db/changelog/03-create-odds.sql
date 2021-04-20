--liquibase formatted sql
--changeset sosadnik:3

CREATE SEQUENCE public.odds_id_seq
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 2147483647
    CACHE 1;

CREATE TABLE public.odds
(
    id integer NOT NULL PRIMARY KEY DEFAULT nextval('odds_id_seq'::regclass),
    won1 double precision,
    draw double precision,
    won2 double precision,
    won1draw double precision,
    won2draw double precision,
    won1won2 double precision
);

