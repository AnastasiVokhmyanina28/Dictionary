CREATE TABLE IF NOT EXISTS public.dictionaries
(
    id     integer          not null generated always as identity(increment 1 start 1 minvalue 1  cache 1 ),
    dictionary character(30) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT dictionaries_pkey PRIMARY KEY(id)
)
    TABLESPACE pg_default;

 ALTER TABLE IF EXISTS public.dictionaries
    OWNER to postgres;

