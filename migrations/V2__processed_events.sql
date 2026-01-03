CREATE TABLE processed_events (
    event_key VARCHAR(200) PRIMARY KEY,
    event_type VARCHAR(60) NOT NULL,
    reservation_id BIGINT NOT NULL,
    processed_at TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

CREATE INDEX idx_processed_events_reservation ON processed_events(reservation_id);
