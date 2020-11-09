DROP TABLE IF EXISTS order_status;
CREATE TABLE order_status (
    id int PRIMARY KEY AUTO_INCREMENT,
    name varchar(60) unique
);

INSERT INTO order_status (name) VALUES
                                    ('Afventende'),
                                    ('Slettet');

ALTER TABLE orders
    ADD status int DEFAULT 1,
    ADD FOREIGN KEY (status) REFERENCES order_status(id);

UPDATE properties SET value = 5 WHERE name = 'version';

