--liquibase formatted sql
--changeset sosadnik:1

CREATE SEQUENCE public.away_team_efficiency_id_seq
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 2147483647
    CACHE 1;

CREATE TABLE public.away_team_efficiency (
    id integer NOT NULL PRIMARY KEY DEFAULT nextval('away_team_efficiency_id_seq'::regclass),
    team_name text NOT NULL,
    won integer NOT NULL,
    lost integer NOT NULL,
    other integer NOT NULL,
    efficacy double precision NOT NULL
);



