drop table  T_USER if exists;
drop table T_ROLE if exists ;
drop table role_perm if exists ;
drop table T_PERMISSION if exists ;

create table T_USER (
    id bigint not null,
    create_time timestamp,
    update_time timestamp,
    name varchar(255),
    password varchar(255),
    role varchar(255),
    primary key (id)
);

create table T_ROLE (
    rid bigint not null,
    name varchar(255),
    desc varchar(255),
    primary key (rid)
);

create table user_role (
    uid bigint not null,
    role_id bigint not null
);

create table role_perm (
    perm_id bigint not null,
    role_id bigint not null
);

create table T_PERMISSION (
    id bigint not null,
    name varchar(255),
    desc varchar(255),
    url varchar (255),
    pid bigint,
    roles varchar(255),
    primary key (id)
);



