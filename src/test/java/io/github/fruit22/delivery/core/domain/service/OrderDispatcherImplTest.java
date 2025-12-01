package io.github.fruit22.delivery.core.domain.service;

import io.github.fruit22.delivery.core.domain.model.Location;
import io.github.fruit22.delivery.core.domain.model.courier.Courier;
import io.github.fruit22.delivery.core.domain.model.order.Order;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;


class OrderDispatcherImplTest {

    private final OrderDispatcher dispatcher = new OrderDispatcherImpl();

    @Test
    void shouldThrowException_ifOrderStatusNotCreated() {
        Order order = new Order(UUID.randomUUID(), new Location(1, 1), 10);
        order.assign(UUID.randomUUID());

        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> dispatcher.dispatch(order, List.of())
        );
    }

    @Test
    void shouldReturnEmpty_ifNoCourierCanTakeOrder() {
        Order order = new Order(UUID.randomUUID(), new Location(1, 1), 11);
        Courier c1 = new Courier("Ivan", 1, new Location(1, 1));
        Courier c2 = new Courier("Mike", 1, new Location(2, 2));
        Optional<Courier> result = dispatcher.dispatch(order, List.of(c1, c2));

        assertTrue(result.isEmpty());
    }

    @Test
    void shouldReturnCourierWithMinimalEta() {
        Order order = new Order(UUID.randomUUID(), new Location(5, 5), 10);
        Courier c1 = new Courier("Ivan", 1, new Location(1, 1));
        Courier c2 = new Courier("Mike", 1, new Location(4, 4));
        Courier c3 = new Courier("Mike", 1, new Location(8, 8));

        Optional<Courier> result = dispatcher.dispatch(order, List.of(c1, c2, c3));

        assertTrue(result.isPresent());
        assertEquals(c2, result.get());
    }
}