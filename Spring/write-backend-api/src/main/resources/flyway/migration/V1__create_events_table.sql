CREATE TABLE IF NOT EXISTS events (
    event_id BIGSERIAL PRIMARY KEY,
    aggregate_uuid UUID NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT now(),
    event_type VARCHAR(100) NOT NULL,
    payload JSONB NOT NULL
);

CREATE INDEX IF NOT EXISTS idx_events_created_at ON events (created_at);
CREATE INDEX IF NOT EXISTS idx_events_aggregate_uuid ON events (aggregate_uuid);