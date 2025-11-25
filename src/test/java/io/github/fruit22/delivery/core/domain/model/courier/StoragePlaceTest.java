package io.github.fruit22.delivery.core.domain.model.courier;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class StoragePlaceTest {

    private final String TEST_NAME = "Test Storage";
    private final int TEST_VOLUME = 100;
    private StoragePlace storagePlace;

    @BeforeEach
    void setUp() {
        storagePlace = new StoragePlace(TEST_NAME, TEST_VOLUME);
    }

    @Test
    void create_NullName_ThrowsException() {
        assertThrows(NullPointerException.class, () -> {
            new StoragePlace(null, TEST_VOLUME);
        });
    }

    @Test
    void create_ZeroVolume_ThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            new StoragePlace(TEST_NAME, 0);
        });
    }

    @Test
    void create_NegativeVolume_ThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            new StoragePlace(TEST_NAME, -10);
        });
    }

    @Test
    void canStore_WhenNotOccupiedAndVolumeFits_ReturnsTrue() {
        assertTrue(storagePlace.canStore(50));
        assertTrue(storagePlace.canStore(100));
    }

    @Test
    void canStore_WhenNotOccupiedButVolumeTooLarge_ReturnsFalse() {
        assertFalse(storagePlace.canStore(150));
    }

    @Test
    void canStore_WhenOccupied_ReturnsFalse() {
        UUID orderId = UUID.randomUUID();
        storagePlace.store(orderId, 50);

        assertFalse(storagePlace.canStore(50));
        assertFalse(storagePlace.canStore(10));
    }

    @Test
    void store_ValidParameters_Success() {
        UUID orderId = UUID.randomUUID();

        storagePlace.store(orderId, 50);

        assertTrue(storagePlace.isOccupied());
    }

    @Test
    void store_WhenAlreadyOccupied_ThrowsException() {
        UUID orderId1 = UUID.randomUUID();
        UUID orderId2 = UUID.randomUUID();

        storagePlace.store(orderId1, 50);

        assertThrows(IllegalStateException.class, () -> {
            storagePlace.store(orderId2, 30);
        });
    }

    @Test
    void store_VolumeTooLarge_ThrowsException() {
        UUID orderId = UUID.randomUUID();

        assertThrows(IllegalStateException.class, () -> {
            storagePlace.store(orderId, 150);
        });
    }

    @Test
    void clear_WithCorrectOrderId_Success() {
        UUID orderId = UUID.randomUUID();
        storagePlace.store(orderId, 50);
        assertTrue(storagePlace.isOccupied());

        storagePlace.clear(orderId);

        assertFalse(storagePlace.isOccupied());
    }

    @Test
    void clear_WithIncorrectOrderId_ThrowsException() {
        UUID orderId1 = UUID.randomUUID();
        UUID orderId2 = UUID.randomUUID();
        storagePlace.store(orderId1, 50);

        assertThrows(IllegalStateException.class, () -> {
            storagePlace.clear(orderId2);
        });
    }

    @Test
    void clear_WhenNotOccupied_ThrowsException() {
        UUID orderId = UUID.randomUUID();

        assertThrows(IllegalStateException.class, () -> {
            storagePlace.clear(orderId);
        });
    }

    @Test
    void isOccupied_WhenEmpty_ReturnsFalse() {
        assertFalse(storagePlace.isOccupied());
    }

    @Test
    void isOccupied_WhenStored_ReturnsTrue() {
        UUID orderId = UUID.randomUUID();
        storagePlace.store(orderId, 50);

        assertTrue(storagePlace.isOccupied());
    }

    @Test
    void equals_SameId_ReturnsTrue() {
        StoragePlace place1 = new StoragePlace("Place1", 100);
        StoragePlace place2 = place1;

        assertTrue(place1.equals(place2));
    }

    @Test
    void equals_DifferentObjects_ReturnsFalse() {
        StoragePlace place1 = new StoragePlace("Place1", 100);
        StoragePlace place2 = new StoragePlace("Place2", 100);

        assertFalse(place1.equals(place2));
    }

    @Test
    void equals_NullObject_ReturnsFalse() {
        StoragePlace place1 = new StoragePlace("Place1", 100);

        assertFalse(place1.equals(null));
    }

}