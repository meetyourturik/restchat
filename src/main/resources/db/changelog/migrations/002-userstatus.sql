-- liquibase formatted sql
-- changeset turik:3
create type user_status AS enum ('active', 'inactive', 'banned');
-- changeset turik:4
alter table "user"
add user_status user_status;
-- changeset turik:5
update "user"
set user_status = 'active';