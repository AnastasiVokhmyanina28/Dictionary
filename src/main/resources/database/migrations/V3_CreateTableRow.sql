CREATE TABLE IF NOT EXISTS public.row
(
    id  integer not null generated always as identity(increment 1 start 1 minvalue 1 cache 1),
    key_word_id bigint not null,
    value_word_id bigint not null,
    constraint row_pkey primary key (id)
    )
    TABLESPACE pg_default;

    ALTER TABLE IF EXISTS public.row
    OWNER to postgres;