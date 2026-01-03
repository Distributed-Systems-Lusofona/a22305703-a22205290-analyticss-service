CREATE TABLE restaurant_metrics (
    restaurant_id BIGINT PRIMARY KEY,
    created_count BIGINT NOT NULL DEFAULT 0,
    confirmed_count BIGINT NOT NULL DEFAULT 0,
    cancelled_count BIGINT NOT NULL DEFAULT 0,
    notified_count BIGINT NOT NULL DEFAULT 0,
    updated_at TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

CREATE INDEX idx_restaurant_metrics_confirmed ON restaurant_metrics(confirmed_count DESC);
