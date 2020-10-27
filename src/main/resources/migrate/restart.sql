drop database if exists chadchat;
drop user if exists 'olskercupcakes'@'localhost';

create database chadchat;
create user 'olskercupcakes'@'localhost';

grant all privileges on chadchat.* to 'olskercupcakes'@'localhost';

use chadchat;
DROP TABLE IF EXISTS users;
create table users(
    id int PRIMARY KEY AUTO_INCREMENT,
    username varchar(25) NOT NULL UNIQUE,
    salt BINARY(16) NOT NULL,
    secret BINARY(32) NOT NULL,
    _date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

DROP TABLE IF EXISTS properties;
CREATE TABLE properties (
    name VARCHAR(255) PRIMARY KEY,
    value VARCHAR(255) NOT NULL
);

INSERT INTO properties (name, value) VALUES ("version", "0");