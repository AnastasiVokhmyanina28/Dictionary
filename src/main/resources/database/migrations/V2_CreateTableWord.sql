CREATE TABLE IF NOT EXISTS public.word
(
    id  integer not null generated always as identity(increment 1 start 1 minvalue 1 cache 1),
    name character varying COLLATE pg_catalog."default" NOT NULL,
    language_id bigint not null,
    constraint word_pkey PRIMARY KEY (id),
    CONSTRAINT language_id FOREIGN KEY (language_id)
        REFERENCES public.language (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION

    )

    TABLESPACE pg_default;

    ALTER TABLE IF EXISTS public.word
    OWNER to postgres;