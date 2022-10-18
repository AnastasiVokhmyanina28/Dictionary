CREATE TABLE IF NOT EXISTS public.row
(
    id  integer not null generated always as identity(increment 1 start 1 minvalue 1 cache 1),
    key_word_id bigint not null,
    value_word_id bigint not null,
    constraint row_pkey primary key (id),
    CONSTRAINT key_word_id FOREIGN KEY (key_word_id)
        REFERENCES public.word (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT value_word_id FOREIGN KEY (value_word_id)
        REFERENCES public.word (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
    )
    TABLESPACE pg_default;

    ALTER TABLE IF EXISTS public.row
    OWNER to postgres;