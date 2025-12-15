package io.github.fruit22.delivery.core.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public record Location(@Column(name = "location_x") int x,
                       @Column(name = "location_y") int y) {

    private static final int MIN_COORDINATE_VALUE = 1;
    private static final int MAX_COORDINATE_VALUE = 10;

    public Location {
        if (x < MIN_COORDINATE_VALUE || x > MAX_COORDINATE_VALUE || y < MIN_COORDINATE_VALUE || y > MAX_COORDINATE_VALUE) {
            throw new IllegalArgumentException("Coordinate values exceed allowed boundaries");
        }
    }

    public int calculateRoute(Location location) {
        return Math.abs(x - location.x()) + Math.abs(y - location.y());
    }
}
