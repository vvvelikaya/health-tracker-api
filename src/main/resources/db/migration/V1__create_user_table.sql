CREATE TABLE user_account (
    id bigserial PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    surname VARCHAR(50) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    birth_date DATE,
    gender VARCHAR(6),
    weight INT,
    role VARCHAR(20) DEFAULT 'ROLE_USER' NOT NULL
);
