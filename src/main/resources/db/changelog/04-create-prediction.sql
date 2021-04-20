--liquibase formatted sql
--changeset sosadnik:4

CREATE SEQUENCE public.prediction_id_seq
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 2147483647
    CACHE 1;

CREATE TABLE public.prediction
(
    id integer NOT NULL PRIMARY KEY DEFAULT nextval('prediction_id_seq'::regclass),
    home_team text NOT NULL,
    away_team text  NOT NULL,
    competition_name text  NOT NULL,
    prediction text NOT NULL,
    competition_cluster text NOT NULL,
    status text  NOT NULL,
    odds_id integer NOT NULL,
    match_date date,
    result text ,
    CONSTRAINT odds FOREIGN KEY (odds_id)
        REFERENCES public.odds (id) MATCH SIMPLE
);

