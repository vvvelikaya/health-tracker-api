CREATE TABLE account (
    id bigserial PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    surname VARCHAR(50) NOT NULL,
    email VARCHAR(255) NOT NULL,
    birth_date DATE,
    gender VARCHAR(6),
    weight INT
);
