create table users
(
    id            serial
        primary key,
    name          varchar,
    phone_number  varchar                 not null
        unique,
    password      varchar                 not null,
    secret_key    varchar,
    registered_at timestamp default now() not null,
    is_active     boolean   default true
);

alter table users
    owner to postgres;

create table cards
(
    id         serial
        primary key,
    card_id    varchar                 not null
        unique,
    user_id    integer,
    balance    double precision,
    is_active  boolean   default true,
    created_at timestamp default now() not null
);

alter table cards
    owner to postgres;

create table history
(
    id                 serial
        primary key,
    card_id            varchar,
    amount             double precision,
    type               varchar,
    created_at         timestamp default now() not null,
    transaction_field  varchar,
    transaction_object varchar
);

alter table history
    owner to postgres;

create table transaction
(
    id            serial
        primary key,
    sender_card   varchar,
    receiver_card varchar,
    amount        double precision,
    created_at    timestamp default now() not null
);

alter table transaction
    owner to postgres;

