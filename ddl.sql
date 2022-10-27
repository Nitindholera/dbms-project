create table user(
	User_id int not null primary key,
    Fname varchar(28),
    Mname varchar(28),
    Lname varchar(28),
    Description varchar(128),
    Email_id varchar(128),
    Password varchar(128),
    Profile_pic varchar(128),
    Date_of_birth date,
    Gender char,
    is_verified bool,
    verify_pin int
);

create table user_phone_number(
	phone_number varchar(10) primary key,
    User_id int,
	FOREIGN KEY (User_id) REFERENCES user(User_id) 
    ON DELETE CASCADE   
    ON UPDATE CASCADE
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
    User_id int,
	Foreign key(User_id) references user(User_id),
    is_admin bool,
	primary key(Group_id, User_id)
);

create table message(
	message_id int not null primary key,
    send_by int,
    foreign key (send_by) references user(User_id),
    time TIME,
    data varchar(256),
    Chat_id int,
    foreign key(Chat_id) references chat(Chat_id)
);

create table chat(
	Chat_id int not null primary key
);

create table friend(
	Friend_id int not null primary key,
    Chat_id int,
    foreign key(Chat_id) references chat(Chat_id)
);

create table is_member_friend(
	Friend_id int,
    Foreign key(Friend_id) references friend(Friend_id),
    User_id int,
	Foreign key(User_id) references user(User_id),
	primary key(Friend_id, User_id)
);

insert into user values(1,"Mohammad", "anas", "khan", "loves kali", "khan@gmail.com", "123", "v.com", "2008-7-04", "m", true, 2345);
select * from user;

