
insert into users (id,created_at, email, fullname, password)
    values (1000000,current_timestamp ,'admin@admin.com','Main Admin','$2a$10$g0fFLA6LLbISU7jTF4vUV.wBYoGYhZfd1maJfw5u9QGG.8Ooj1bYi');

insert into users_roles (users_id,roles_id)
    values (1000000,1);

insert into users_roles (users_id,roles_id)
    values (1000000,2);

insert into users_roles (users_id,roles_id)
    values (1000000,3);

insert into users (id,created_at, email, fullname, password)
values (100000,current_timestamp ,'moderator@moderator.com','Main Moderator','$2a$10$xrYEOzF73udis7G5CdoG7OebhedvGUFXaiRm76DVBQR1GFZr0rM3i');

insert into users_roles (users_id,roles_id)
values (100000,1);

insert into users_roles (users_id,roles_id)
values (100000,2);
