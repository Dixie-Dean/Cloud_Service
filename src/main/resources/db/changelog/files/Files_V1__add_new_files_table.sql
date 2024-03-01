create table cloud_schema.files (
    email varchar(255) not null,
    file varchar(255),
    filename varchar(255) not null,
    hash varchar(255) not null,
    primary key (hash)
)