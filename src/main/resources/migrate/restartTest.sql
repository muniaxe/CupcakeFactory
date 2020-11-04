drop database if exists olskercupcakes_test;
create database olskercupcakes_test;

use olskercupcakes_test;
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

INSERT INTO properties (name, value) VALUES ('version', '0');