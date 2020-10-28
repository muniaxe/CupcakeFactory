ALTER TABLE users
ADD balance int NOT NULL DEFAULT (0);

UPDATE properties SET value = 1 WHERE name = 'version';