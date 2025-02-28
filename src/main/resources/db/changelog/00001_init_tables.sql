CREATE TABLE users
(
    id          uuid PRIMARY KEY default gen_random_uuid(),
    external_id uuid        NOT NULL,
    first_name  VARCHAR(50) NOT NULL,
    last_name   VARCHAR(50) NOT NULL
);