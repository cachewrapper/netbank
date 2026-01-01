CREATE TABLE IF NOT EXISTS account_aggregates (
    aggregate_uuid UUID NOT NULL,
    username VARCHAR(100) UNIQUE,
    balance NUMERIC(19, 4) NOT NULL
);