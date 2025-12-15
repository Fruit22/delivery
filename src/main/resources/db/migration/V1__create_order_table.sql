CREATE TABLE delivery_order
(
    id         UUID PRIMARY KEY,
    location_x INT  NOT NULL,
    location_y INT  NOT NULL,
    volume     INT  NOT NULL,
    status     TEXT NOT NULL,
    courier_id UUID
);