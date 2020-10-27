drop database if exists olskercupcakes;
drop user if exists 'olskercupcakes'@'localhost';

create database olskercupcakes;
create user 'olskercupcakes'@'localhost';

grant all privileges on olskercupcakes.* to 'olskercupcakes'@'localhost';

use olskercupcakes;
DROP TABLE IF EXISTS users;
create table users(
    id int PRIMARY KEY AUTO_INCREMENT,
    email varchar(25) NOT NULL UNIQUE,
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