CREATE TABLE record (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT,
    heart_rate INT,
    sugar_level NUMERIC(3,1),
    date_created DATE DEFAULT CURRENT_DATE NOT NULL,
    date_updated DATE DEFAULT CURRENT_DATE NOT NULL,
    constraint record_user_account_id_fk
        foreign key (user_id) references user_account
);