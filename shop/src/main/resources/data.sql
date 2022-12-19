drop table if exists evententry;

create table evententry (
    id int not null AUTO_INCREMENT PRIMARY KEY,
    type varchar(255),
    content_type varchar(255),
    payload varchar(5555),
    timestamp datetime
);