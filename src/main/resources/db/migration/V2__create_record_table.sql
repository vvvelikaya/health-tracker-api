CREATE TABLE record (
    id bigserial PRIMARY KEY,
    heart_rate INT,
    sugar_level NUMERIC(3,1),
    date_created DATE DEFAULT CURRENT_DATE NOT NULL,
    date_updated DATE DEFAULT CURRENT_DATE NOT NULL
);