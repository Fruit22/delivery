package io.github.fruit22.delivery.core.domain.model.courier;

import io.github.fruit22.delivery.core.domain.model.Location;
import io.github.fruit22.delivery.core.domain.model.order.Order;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class CourierTest {

    @Test
    void createCourier_WithValidArgs_CourierCreated() {
        Location location = new Location(1, 1);
        Courier courier = new Courier("John", 10, location);

        assertNotNull(courier.getId());
        assertEquals("John", courier.getName());
    }

    @Test
    void createCourier_WithNullName_ThrowsException() {
        assertThrows(IllegalArgumentException.class,
                () -> new Courier(null, 10, new Location(0, 0)));
    }

    @Test
    void createCourier_WithInvalidSpeed_ThrowsException() {
        assertThrows(IllegalArgumentException.class,
                () -> new Courier("John", 0, new Location(0, 0)));
    }

    @Test
    void createCourier_WithNullLocation_ThrowsException() {
        assertThrows(NullPointerException.class,
                () -> new Courier("John", 10, null));
    }

    @Test
    void addStoragePlace_WithValidStoragePlace_AddedToList() {
        Courier courier = new Courier("John", 10, new Location(1, 1));

        StoragePlace extra = new StoragePlace("backpack", 20);
        courier.addStoragePlace(extra);

        assertTrue(
                courier.getStoragePlaces().contains(extra)
        );
    }

    @Test
    void addStoragePlace_WithNull_ThrowsException() {
        Courier courier = new Courier("John", 10, new Location(1, 1));

        assertThrows(NullPointerException.class,
                () -> courier.addStoragePlace(null));
    }

    @Test
    void canTakeOrder_WithOrderThatFits_ReturnsTrue() {
        Courier courier = new Courier("John", 10, new Location(1, 1));
        Order order = new Order(UUID.randomUUID(), new Location(1, 1), 5);

        assertTrue(courier.canTakeOrder(order));
    }

    @Test
    void canTakeOrder_WithOrderTooBig_ReturnsFalse() {
        Courier courier = new Courier("John", 10, new Location(1, 1));
        Order order = new Order(UUID.randomUUID(), new Location(1, 1), 11);

        assertFalse(courier.canTakeOrder(order));
    }

    @Test
    void canTakeOrder_WithNull_ThrowsException() {
        Courier courier = new Courier("John", 10, new Location(1, 1));

        assertThrows(NullPointerException.class,
                () -> courier.canTakeOrder(null));
    }

    @Test
    void takeOrder_WithFittingOrder_StoredInStoragePlace() {
        Courier courier = new Courier("John", 10, new Location(1, 1));
        Order order = new Order(UUID.randomUUID(), new Location(1, 1), 5);

        courier.takeOrder(order);

        assertTrue(
                courier.getStoragePlaces()
                        .stream()
                        .anyMatch(StoragePlace::isOccupied)
        );
    }

    @Test
    void takeOrder_WhenNoSpaceAvailable_ThrowsException() {
        Courier courier = new Courier("John", 10, new Location(1, 1));
        courier.addStoragePlace(new StoragePlace("backpack", 20));

        Order huge = new Order(UUID.randomUUID(), new Location(1, 1), 31);

        assertThrows(IllegalStateException.class,
                () -> courier.takeOrder(huge));
    }

    @Test
    void completeOrder_WhenOrderInStorage_StorageCleared() {
        Courier courier = new Courier("Mike", 10, new Location(1, 1));
        Order order = new Order(UUID.randomUUID(), new Location(1, 1), 5);

        courier.takeOrder(order);
        courier.completeOrder(order);

        assertTrue(
                courier.getStoragePlaces()
                        .stream()
                        .noneMatch(StoragePlace::isOccupied)
        );
    }

    @Test
    void completeOrder_WhenOrderNotInStorage_ThrowsException() {
        Courier courier = new Courier("John", 10, new Location(1, 1));
        Order order = new Order(UUID.randomUUID(), new Location(1, 1), 5);

        assertThrows(IllegalStateException.class,
                () -> courier.completeOrder(order));
    }

    @Test
    void calculateTimeToLocation_WithDifferentLocation_ReturnsCorrectTime() {
        Courier courier = new Courier("John", 2, new Location(1, 1));
        Location target = new Location(7, 9);

        // distance будет зависеть от реализации calculateRoute()
        double time = courier.calculateTimeToLocation(target);

        assertEquals(7, time);
    }

    @Test
    void calculateTimeToLocation_WithNullLocation_ThrowsException() {
        Courier courier = new Courier("John", 10, new Location(1, 1));

        assertThrows(NullPointerException.class,
                () -> courier.calculateTimeToLocation(null));
    }

    @Test
    void move_WhenTargetWithinSpeed_MovesDirectlyToTarget() {
        Courier courier = new Courier("John", 10, new Location(1, 1));
        courier.move(new Location(6, 6));

        assertEquals(6, courier.getLocation().x());
        assertEquals(6, courier.getLocation().y());
    }

    @Test
    void move_WhenTargetTooFar_MovesOnlyWithinSpeedLimit() {
        Courier courier = new Courier("John", 5, new Location(1, 1));
        courier.move(new Location(3, 5));

        assertEquals(3, courier.getLocation().x());
        assertEquals(4, courier.getLocation().y());
    }

    @Test
    void move_WithNullTarget_ThrowsException() {
        Courier courier = new Courier("John", 10, new Location(1, 1));

        assertThrows(NullPointerException.class,
                () -> courier.move(null));
    }
}