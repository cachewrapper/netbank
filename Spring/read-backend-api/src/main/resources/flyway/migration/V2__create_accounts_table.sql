CREATE TABLE IF NOT EXISTS accounts (
    account_uuid UUID PRIMARY KEY,
    balance NUMERIC(19, 4) NOT NULL
);