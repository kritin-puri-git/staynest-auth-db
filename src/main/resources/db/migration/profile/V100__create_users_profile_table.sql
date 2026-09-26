CREATE TABLE users_profile(
    id BIGINT NOT NULL,
    public_id VARBINARY(512) NOT NULL,
    public_id_hash VARBINARY(512) NOT NULL,

    name VARBINARY(512),
    bio VARBINARY(1000),
    profile_picture_url VARBINARY(5000),
    date_of_birth VARBINARY(100),
    gender VARBINARY(100),

    encryption_version SMALLINT UNSIGNED NOT NULL,
    encryption_key_id SMALLINT UNSIGNED NOT NULL,
    hashing_version SMALLINT UNSIGNED NOT NULL,
    hashing_key_id SMALLINT UNSIGNED NOT NULL,

    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,

    CONSTRAINT pk_users_profile_id
        PRIMARY KEY (id),
    CONSTRAINT uq_users_profile_public_id_hash
        UNIQUE (public_id_hash)
)