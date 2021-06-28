create table tweet
(
    id               SERIAL PRIMARY KEY,
    message          varchar(1024)                       not null,
    create_date      timestamp default CURRENT_TIMESTAMP not null,
    last_update_date timestamp default CURRENT_TIMESTAMP not null
);