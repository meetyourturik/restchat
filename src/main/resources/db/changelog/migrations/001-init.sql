-- liquibase formatted sql
-- changeset turik:1
create sequence if not exists user_id;
create sequence if not exists message_id;
create sequence if not exists friendship_request_id;

create type request_status AS ENUM ('REQUESTED', 'DECLINED', 'APPROVED');
create type user_status AS ENUM ('ACTIVE', 'INACTIVE', 'BANNED');
create type chat_permission AS ENUM ('EVERYONE', 'FRIENDS_ONLY');
create type report_reason AS ENUM ('SPAM', 'ABUSE', 'VIOLENCE', 'OTHER');

create table "user" (
    id bigint NOT NULL DEFAULT nextval('user_id'),
    username character varying(25) NOT NULL,
    email character varying(250) NOT NULL,
    timezone character varying(10) NOT NULL,
    language character varying(10) NOT NULL,
    user_status user_status NOT NULL,
    deletion_date timestamp with time zone NULL,
    chat_permission chat_permission NOT NULL,
    ip inet NOT NULL,
    PRIMARY KEY (id)
);

create table message (
    id bigint NOT NULL DEFAULT nextval('message_id'),
    sender_id bigint NOT NULL,
    receiver_id bigint NOT NULL,
    text text NOT NULL,
    created timestamp with time zone NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT sender_id FOREIGN KEY (sender_id)
        REFERENCES "user" (id) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE CASCADE
        NOT VALID,
    CONSTRAINT receiver_id FOREIGN KEY (receiver_id)
        REFERENCES "user" (id) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE CASCADE
        NOT VALID
);

create table friendship_request (
    id bigint NOT NULL DEFAULT nextval('friendship_request_id'),
    sender_id bigint NOT NULL,
    receiver_id bigint NOT NULL,
    status request_status NOT NULL,
    text text NULL,
    created timestamp with time zone NOT NULL,
    PRIMARY KEY (sender_id , receiver_id),
    CONSTRAINT sender_id FOREIGN KEY (sender_id)
        REFERENCES "user" (id) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE CASCADE
        NOT VALID,
    CONSTRAINT receiver_id FOREIGN KEY (receiver_id)
        REFERENCES "user" (id) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE CASCADE
        NOT VALID
);

create table friendship (
    user1_id bigint NOT NULL,
    user2_id bigint NOT NULL,
    CONSTRAINT user1_id FOREIGN KEY (user1_id)
        REFERENCES "user" (id) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE CASCADE
        NOT VALID,
    CONSTRAINT user2_id FOREIGN KEY (user2_id)
        REFERENCES "user" (id) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE CASCADE
        NOT VALID
);

create table banned_ip (
	ip inet NOT NULL,
	PRIMARY KEY (ip)
);

create table user_report (
	reporter_id bigint NOT NULL,
	reported_id bigint NOT NULL,
    report_reason report_reason NOT NULL,
	CONSTRAINT reporter_id FOREIGN KEY (reporter_id)
        REFERENCES "user" (id) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE CASCADE
        NOT VALID,
	CONSTRAINT reported_id FOREIGN KEY (reported_id)
        REFERENCES "user" (id) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE CASCADE
        NOT VALID,
	PRIMARY KEY (reporter_id, reported_id)
);

create table deleted_message (
    id bigint NOT NULL,
    sender_id bigint NOT NULL,
    receiver_id bigint NOT NULL,
    created timestamp with time zone NOT NULL,
    deleted timestamp with time zone NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT sender_id FOREIGN KEY (sender_id)
        REFERENCES "user" (id) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE CASCADE
        NOT VALID,
    CONSTRAINT receiver_id FOREIGN KEY (receiver_id)
        REFERENCES "user" (id) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE CASCADE
        NOT VALID
);

comment on column "user".deletion_date is 'date when inactive user is deleted from system';
comment on column "user".ip is 'most recent ip of user login';
