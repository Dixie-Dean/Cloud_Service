alter table if exists cloud_schema.files
    add constraint FK
        foreign key (email)
            references cloud_schema.users (email)