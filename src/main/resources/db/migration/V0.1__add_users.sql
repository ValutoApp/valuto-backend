CREATE TABLE users (
    id UUID PRIMARY KEY,
    email VARCHAR(256) NOT NULL
);

CREATE table user_credentials (
    user_id UUID PRIMARY KEY REFERENCES users (id) ON DELETE CASCADE,
    password_hash TEXT NOT NULL
);

CREATE UNIQUE INDEX users_email ON users (email);