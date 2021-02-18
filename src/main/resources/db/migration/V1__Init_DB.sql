create sequence hibernate_sequence start 1 increment 1;
create table correct_answer
(
    id             int8 generated by default as identity,
    correct_answer varchar(2048),
    primary key (id)
);
create table question
(
    id           int8 generated by default as identity,
    answer       varchar(255),
    question     varchar(2048),
    test_options bytea,
    primary key (id)
);
create table user_role
(
    user_id int8 not null,
    roles   varchar(255)
);
create table usr
(
    id       int8    not null,
    active   boolean not null,
    password varchar(255) not null,
    username varchar(255) not null,
    primary key (id)
);
alter table if exists user_role
    add constraint user_role_user_fk
        foreign key (user_id) references usr;