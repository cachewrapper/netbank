CREATE TABLE account_replica (
    account_uuid UUID PRIMARY KEY,
    balance NUMERIC(19,4) NOT NULL DEFAULT 0,
    last_event_version BIGINT NOT NULL DEFAULT 0,
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT NOW()
);

CREATE OR REPLACE FUNCTION set_updated_at()
RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_at = NOW();
RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trg_set_updated_at
    BEFORE UPDATE ON account_replica
    FOR EACH ROW
    EXECUTE FUNCTION set_updated_at();

CREATE INDEX idx_account_replica_last_event_version
    ON account_replica(last_event_version);
