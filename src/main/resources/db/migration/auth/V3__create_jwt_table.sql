create table jwt(
    id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,

    subject VARCHAR(36) NOT NULL,

    refresh_jwt_id VARCHAR(36) NOT NULL,
    access_jwt_id VARCHAR(36) NOT NULL,

    session_id VARCHAR(36) Not NULL,

    refresh_expires_at TIMESTAMP NOT NULL,

    status VARCHAR(30) NOT NULL,
    role VARCHAR(30) NOT NULL,

    device_id VARCHAR(36) NOT NULL,
    user_agent TEXT NOT NULL,

    CONSTRAINT pk_jwt_id
                PRIMARY KEY (id),
    CONSTRAINT uq_jwt_refresh_jwt_id
                UNIQUE (refresh_jwt_id),
    CONSTRAINT uq_jwt_access_jwt_id
                UNIQUE (access_jwt_id),
    CONSTRAINT uq_jwt_session_id
                UNIQUE (session_id),

    INDEX idx_jwt_subject (subject)
);