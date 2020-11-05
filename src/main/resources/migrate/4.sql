DROP TABLE IF EXISTS orders;
DROP TABLE IF EXISTS order_items;

CREATE TABLE orders(
    uuid varchar(36) unique PRIMARY KEY not null,
    user int not null,
    _date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP not null,
    FOREIGN KEY (user) REFERENCES users(id)
);

CREATE TABLE order_items(
  order_uuid varchar(36) not null,
  cake_id int not null,
  topping_id int not null,
  quantity int not null,

  primary key (order_uuid, cake_id, topping_id),

  FOREIGN KEY (order_uuid) REFERENCES orders(uuid),
  FOREIGN KEY (cake_id) REFERENCES cupcake_cakes(id),
  FOREIGN KEY (topping_id) references cupcake_toppings(id)
);

UPDATE properties SET value = 4 WHERE name = 'version';