    CREATE TABLE IF NOT EXISTS public.dictionary_values
    (
    id  integer not null generated always as identity(increment 1 start 1 minvalue 1 cache 1),
    id_words bigint not null,
    word character varying COLLATE pg_catalog."default" NOT NULL,
    translation character varying COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT dictionary_values_pkey PRIMARY KEY (id),
    CONSTRAINT word FOREIGN KEY (id_words)

    REFERENCES public.words (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION
)
    TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.dictionary_values
    OWNER to postgres;





