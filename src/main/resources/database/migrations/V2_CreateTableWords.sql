    CREATE TABLE IF NOT EXISTS public.words
    (
    id  integer not null generated always as identity(increment 1 start 1 minvalue 1 cache 1),
    word bigint not null,
    translation bigint not null,
    constraint words_pkey primary key(id),
    CONSTRAINT translate FOREIGN KEY (translation)
    REFERENCES public.dictionaries (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION,
    CONSTRAINT word FOREIGN KEY (word)
    REFERENCES public.dictionaries (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION
    )
    TABLESPACE pg_default;

    ALTER TABLE IF EXISTS public.words
    OWNER to postgres;