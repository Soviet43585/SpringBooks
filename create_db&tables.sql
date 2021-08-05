create database springmvc;

use springmvc;
create table Book
(
    id     int primary key auto_increment,
    title  nvarchar(50),
    author nvarchar(50)
);