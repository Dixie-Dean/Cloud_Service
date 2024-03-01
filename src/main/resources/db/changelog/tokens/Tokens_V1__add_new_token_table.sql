create table cloud_schema.token (
    auth_token varchar(255) not null,
    revoked boolean not null,
    primary key (auth_token)
)