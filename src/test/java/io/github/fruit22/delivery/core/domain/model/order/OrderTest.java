package io.github.fruit22.delivery.core.domain.model.order;

import io.github.fruit22.delivery.core.domain.model.Location;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class OrderTest {

    @Test
    void createOrder_WithValidData_OrderCreated() {
        UUID id = UUID.randomUUID();
        Location location = new Location(5, 10);
        int volume = 5;

        Order order = new Order(id, location, volume);

        assertEquals(id, order.getId());
        assertEquals(location, order.getLocation());
        assertEquals(volume, order.getVolume());
        assertEquals(OrderStatus.CREATED, order.getStatus());
    }

    @Test
    void createOrder_WithNullId_ThrowsException() {
        Location location = new Location(5, 10);

        assertThrows(NullPointerException.class,
                () -> new Order(null, location, 10));
    }

    @Test
    void createOrder_WithNullLocation_ThrowsException() {
        UUID id = UUID.randomUUID();

        assertThrows(NullPointerException.class,
                () -> new Order(id, null, 10));
    }

    @Test
    void createOrder_WithInvalidVolume_ThrowsException() {
        UUID id = UUID.randomUUID();
        Location location = new Location(5, 10);

        assertThrows(IllegalArgumentException.class,
                () -> new Order(id, location, 0));
    }

    @Test
    void assignCourier_WithValidId_StatusAssigned() {
        UUID id = UUID.randomUUID();
        Location location = new Location(5, 10);
        Order order = new Order(id, location, 5);
        UUID courierId = UUID.randomUUID();

        order.assign(courierId);

        assertEquals(OrderStatus.ASSIGNED, order.getStatus());
        assertEquals(courierId, order.getCourierId());
    }

    @Test
    void assignCourier_WithNullCourierId_ThrowsException() {
        Order order = new Order(UUID.randomUUID(), new Location(5, 10), 5);

        assertThrows(NullPointerException.class,
                () -> order.assign(null));
    }

    @Test
    void completeOrder_WhenAssigned_StatusCompleted() {
        Order order = new Order(UUID.randomUUID(), new Location(5, 10), 5);
        order.assign(UUID.randomUUID());

        order.completed();

        assertEquals(OrderStatus.COMPLETED, order.getStatus());
    }

    @Test
    void completeOrder_WhenNotAssigned_ThrowsException() {
        Order order = new Order(UUID.randomUUID(), new Location(5, 10), 5);

        assertThrows(IllegalStateException.class,
                order::completed);
    }
}