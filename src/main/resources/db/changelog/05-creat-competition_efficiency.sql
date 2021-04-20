--liquibase formatted sql
--changeset sosadnik:5

CREATE SEQUENCE public.competition_efficiency_id_seq
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 2147483647
    CACHE 1;

CREATE TABLE public.competition_efficiency
(
    id integer NOT NULL PRIMARY KEY DEFAULT nextval('competition_efficiency_id_seq'::regclass),
    competition_name text NOT NULL,
    won integer NOT NULL,
    lost integer NOT NULL,
    other integer NOT NULL,
    efficacy double precision NOT NULL
);

