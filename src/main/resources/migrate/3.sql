ALTER TABLE users
    ADD is_admin boolean NOT NULL DEFAULT (false);

UPDATE properties SET value = 3 WHERE name = 'version';