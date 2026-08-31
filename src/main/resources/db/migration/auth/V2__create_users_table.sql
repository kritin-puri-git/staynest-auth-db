CREATE TABLE users(
    lookup_id BIGINT UNSIGNED NOT NULL,

    public_id VARBINARY(512) NOT NULL,
    username VARBINARY(512) NOT NULL,
    email VARBINARY(512) NOT NULL,

    encryption_version SMALLINT UNSIGNED NOT NULL,
    encryption_key_id SMALLINT UNSIGNED NOT NULL,

    status VARCHAR(50) NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,


    CONSTRAINT pk_users_lookup_id
        PRIMARY KEY (lookup_id),
    CONSTRAINT fk_users_lookup_id
        FOREIGN KEY (lookup_id)
            REFERENCES users_lookup(id)
                  ON DELETE CASCADE

);