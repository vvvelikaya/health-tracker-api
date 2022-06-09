alter table health_tracker.user_account
    add password varchar(255) not null;

alter table health_tracker.user_account
    add role varchar(20) not null default 'ROLE_USER';