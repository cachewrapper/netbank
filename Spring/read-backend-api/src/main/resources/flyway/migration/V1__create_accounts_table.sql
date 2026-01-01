CREATE TABLE IF NOT EXISTS accounts (
    account_uuid UUID PRIMARY KEY,
    username VARCHAR(100) UNIQUE,
    balance NUMERIC(19, 4) NOT NULL
);

CREATE UNIQUE INDEX IF NOT EXISTS idx_accounts_username ON accounts(username);