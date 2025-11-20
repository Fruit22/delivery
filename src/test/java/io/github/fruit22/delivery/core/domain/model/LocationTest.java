package io.github.fruit22.delivery.core.domain.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LocationTest {

    @Test
    void createValidLocation() {
        Location location = new Location(5, 5);
        assertEquals(5, location.x());
        assertEquals(5, location.y());
    }

    @Test
    void createLocationWithInvalidCoordinates_ThrowsException() {
        assertAll(
                () -> assertThrows(IllegalArgumentException.class, () -> new Location(0, 5)),
                () -> assertThrows(IllegalArgumentException.class, () -> new Location(5, 0)),
                () -> assertThrows(IllegalArgumentException.class, () -> new Location(11, 5)
                ));
    }

    @Test
    void calculateRoute_SameLocation_ReturnsZero() {
        Location loc = new Location(3, 4);
        assertEquals(0, loc.calculateRoute(loc));
    }

    @Test
    void calculateRoute_DifferentLocations_ReturnsCorrectDistance() {
        Location loc1 = new Location(1, 1);
        Location loc2 = new Location(4, 5);
        assertEquals(7, loc1.calculateRoute(loc2));
    }

    @Test
    void equals_SameCoordinates_ReturnsTrue() {
        Location loc1 = new Location(3, 4);
        Location loc2 = new Location(3, 4);
        assertEquals(loc1, loc2);
    }

    @Test
    void equals_DifferentCoordinates_ReturnsFalse() {
        Location loc1 = new Location(3, 4);
        Location loc2 = new Location(3, 5);
        assertNotEquals(loc1, loc2);
    }

    @Test
    void equals_Null_ReturnsFalse() {
        Location loc = new Location(3, 4);
        assertNotEquals(null, loc);
    }

}