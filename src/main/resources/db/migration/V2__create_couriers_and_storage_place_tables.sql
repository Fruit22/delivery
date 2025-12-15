CREATE TABLE courier
(
    id         UUID PRIMARY KEY,
    name       TEXT NOT NULL,
    speed      INT  NOT NULL,
    location_x INT  NOT NULL,
    location_y INT  NOT NULL
);

CREATE TABLE storage_place
(
    id           UUID PRIMARY KEY,
    name         TEXT NOT NULL,
    total_volume INT  NOT NULL,
    order_id     UUID,
    courier_id   UUID,
    CONSTRAINT fk_storage_courier
        FOREIGN KEY (courier_id)
            REFERENCES courier (id)
            ON DELETE SET NULL
);