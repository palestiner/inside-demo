create sequence hibernate_sequence start 1 increment 1;
create table app_user (id int8 not null, password varchar(255) not null, username varchar(255) not null, primary key (id));
create table details (id int8 not null, age int4 not null, primary key (id));
create table user_details (details_id int8, user_id int8 not null, primary key (user_id));
alter table if exists app_user add constraint UK_3k4cplvh82srueuttfkwnylq0 unique (username);
alter table if exists user_details add constraint FKb2ykdv13ox3dpj2mvl7b3l8pb foreign key (details_id) references details;
alter table if exists user_details add constraint FK9gi3puv1xjf9opa5eff9ojpo0 foreign key (user_id) references app_user;
