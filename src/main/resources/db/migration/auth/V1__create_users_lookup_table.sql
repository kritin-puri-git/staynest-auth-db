CREATE TABLE users_lookup(
    id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,

    public_id_index BINARY(64) NOT NULL,
    username_index BINARY(64) NOT NULL,
    email_index BINARY(64) NOT NULL,

    hashing_version SMALLINT UNSIGNED NOT NULL,
    hashing_key_id SMALLINT UNSIGNED NOT NULL,

    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
                         ON UPDATE CURRENT_TIMESTAMP,

    CONSTRAINT pk_user_lookup_id
                         PRIMARY KEY (id),
    CONSTRAINT uq_users_lookup_public_id_index
                         UNIQUE(public_id_index),
    CONSTRAINT uq_users_lookup_username_index
                         UNIQUE (username_index),
    CONSTRAINT uq_users_lookup_email_index
                         UNIQUE(email_index)
);