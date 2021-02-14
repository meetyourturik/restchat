-- liquibase formatted sql
-- changeset turik:1
create sequence if not exists user_id;
-- changeset turik:2
create table "user" (
    id bigint not null default nextval('user_id'),
    username character varying(25) NOT NULL,
    primary key (id)
);