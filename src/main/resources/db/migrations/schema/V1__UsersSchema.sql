CREATE TABLE IF NOT EXISTS fpl_users
(
    user_id character varying(255) not null,
    team_id bigint not null,
    first_name character varying(255),
    last_name character varying(255),
    email  character varying (255) unique not null,
    username  character varying (255) not null,
    roles  character varying (255) not null,
    enabled boolean not null,
    credential_expired boolean not null,
    account_locked boolean not null,
    last_login timestamp,
    created_by character varying (255),
    created_date timestamp,
    last_modified_by character varying (255),
    last_modified_date timestamp,
    PRIMARY KEY (user_id)
);
