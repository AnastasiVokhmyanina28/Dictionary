CREATE TABLE IF NOT EXISTS public.language
(
    id  integer   not null generated always as identity(increment 1 start 1 minvalue 1 cache 1),
    name character varying COLLATE pg_catalog."default" NOT NULL,
    pattern character varying COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT language_pkey PRIMARY KEY(id)
)
    TABLESPACE pg_default;

    ALTER TABLE IF EXISTS public.language
    OWNER TO postgres;
