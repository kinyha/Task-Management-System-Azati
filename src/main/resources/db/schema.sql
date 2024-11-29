DROP TABLE IF EXISTS tasks;
DROP TABLE IF EXISTS users;

CREATE TABLE IF NOT EXISTS users
(
    id       UUID PRIMARY KEY,
    username VARCHAR(50)  NOT NULL UNIQUE,
    email    VARCHAR(100) NOT NULL,
    password VARCHAR(100) NOT NULL,
    role     VARCHAR(20)  NOT NULL
);

CREATE TABLE IF NOT EXISTS tasks
(
    id               UUID PRIMARY KEY,
    title            VARCHAR(100) NOT NULL,
    description      VARCHAR(500),
    status           VARCHAR(20)  NOT NULL,
    priority         VARCHAR(20)  NOT NULL,
    assigned_user_id UUID REFERENCES users (id),
    created_by       UUID         NOT NULL REFERENCES users (id),
    created_at       TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at       TIMESTAMP,
    deadline         TIMESTAMP
);