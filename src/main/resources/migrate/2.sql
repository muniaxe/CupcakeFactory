drop table if exists cupcake_cakes;
drop table if exists cupcake_toppings;

create table cupcake_toppings(
    id int PRIMARY KEY AUTO_INCREMENT,
    name varchar(25) unique,
    price int
);

create table cupcake_cakes(
    id int primary key auto_increment,
    name varchar(25) unique,
    price int
);

INSERT INTO cupcake_toppings(name, price) values
                                                 ("Chocolate", 500),
                                                 ("Blueberry", 500),
                                                 ("Raspberry", 500),
                                                 ("Crispy", 600),
                                                 ("Strawberry", 600),
                                                 ("Rum/Raisin", 700),
                                                 ("Orange", 800),
                                                 ("Lemon", 800),
                                                 ("Blue Cheese", 900);


insert into cupcake_cakes(name, price) values
                                              ("Chocolate", 500),
                                              ("Vanilla", 500),
                                              ("Nutmeg", 500),
                                              ("Pistacio", 600),
                                              ("Almond", 700);

UPDATE properties SET value = 2 WHERE name = 'version';