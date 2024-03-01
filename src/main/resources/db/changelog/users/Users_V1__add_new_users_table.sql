create table cloud_schema.users (
    user_id bigserial not null,
    email varchar(255) not null unique,
    username varchar(255) not null,
    lastname varchar(255) not null,
    password varchar(255) not null,
    roles varchar(255),
    primary key (user_id)
)