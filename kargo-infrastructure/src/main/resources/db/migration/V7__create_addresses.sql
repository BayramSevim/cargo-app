CREATE TABLE addresses (
    id BIGSERIAL PRIMARY KEY,
    shipment_id BIGINT NOT NULL REFERENCES shipments(id),
    address_type VARCHAR(20) NOT NULL,
    full_address VARCHAR(500) NOT NULL,
    city VARCHAR(100) NOT NULL,
    district VARCHAR(100),
    postal_code VARCHAR(10)
);

CREATE INDEX idx_addresses_shipment_id ON addresses (shipment_id);