drop database mediabase;

create database mediabase;

use mediabase;

create table user(
	User_name varchar(28) not null primary key,
    Fname varchar(28),
    Lname varchar(28),
    Description varchar(128),
    Email_id varchar(128),
    Password varchar(128),
    Profile_pic varchar(128),
    Date_of_birth date,
    Gender char,
    is_verified bool,
    verify_pin int,
    token varchar(8)
);

create table user_phone_number(
	phone_number varchar(10) primary key,
    User_name varchar(28),
	FOREIGN KEY (User_name) REFERENCES user(User_name) 
    ON DELETE CASCADE   
    ON UPDATE CASCADE
);

create table chat(
	Chat_id int not null primary key
);

create table group_data(
	Group_id int not null primary key,
    name varchar(28),
    description varchar(128),
    picture varchar(128),       
    chat_id int,
    foreign key(chat_id) references chat(Chat_id)
);

create table is_member_group(
	Group_id int,
    Foreign key(Group_id) references group_data(Group_id),
    User_name varchar(28),
	Foreign key(User_name) references user(User_name),
    is_admin bool,
	primary key(Group_id, User_name)
);

create table message(
	message_id int not null primary key,
    send_by varchar(28),
    foreign key (send_by) references user(User_name),
    time datetime,
    data varchar(256),
    Chat_id int,
    foreign key(Chat_id) references chat(Chat_id)
);



create table friend(
	Friend_id int not null primary key,
    Chat_id int,
    foreign key(Chat_id) references chat(Chat_id)
);

create table is_member_friend(
	Friend_id int,
    Foreign key(Friend_id) references friend(Friend_id),
    User_name varchar(28),
	Foreign key(User_name) references user(User_name),
	primary key(Friend_id, User_name)
);

create table friend_requests(
	sender varchar(28),
	receiver varchar(28),
	Foreign key(sender) references user(User_name),
	Foreign key(receiver) references user(User_name),
	primary key(sender,receiver)
);

create table group_invites(
	User_name varchar(28),
	Group_id int,
	Foreign key(User_name) references user(User_name),
	Foreign key(Group_id) references group_data(Group_id),
	primary key(User_name,Group_id)
);
-- insert into user values("anus", "Mohammad", "anas", "khan", "loves kali", "khan@gmail.com", "123", "v.com", "2008-7-04", "m", true, 2345);
select * from user;
select * from friend_requests;
select * from friend;
-- insert into friend_requests values("deva", "deva1"); 
select * from group_data;
select max(Friend_id) as Friend_id from friend;
select * from is_member_group;
select * from group_data;
select * from group_invites;
select * from friend_requests;