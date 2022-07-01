create sequence hibernate_sequence start 1 increment 1;
create table app_user (id int8 not null, password varchar(255) not null, username varchar(255) not null, primary key (id));
create table message (id int8 not null, content varchar(255) not null, user_id int8, primary key (id));
alter table if exists app_user add constraint UK_unique_username unique (username);
alter table if exists message add constraint FK_message_user foreign key (user_id) references app_user;
insert into app_user (id, password, username)values (1, '$2a$10$slYQmyNdGzTn7ZLBXBChFOC9f6kFjAqPhccnP6DxlWXx2lPk1C3G6', 'user');
