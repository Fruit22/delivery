package io.github.fruit22.delivery.adapter.out.postgres.order;

import io.github.fruit22.delivery.adapter.out.postgres.BasePostgresContainerTest;
import io.github.fruit22.delivery.core.domain.model.Location;
import io.github.fruit22.delivery.core.domain.model.order.Order;
import io.github.fruit22.delivery.core.domain.model.order.OrderStatus;
import io.github.fruit22.delivery.core.port.OrderRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Import(OrderRepositoryImpl.class)
class OrderRepositoryImplTest extends BasePostgresContainerTest {

    @Autowired
    private OrderRepository orderRepository;

    @Test
    void shouldSaveAndFindOrderById() {
        Order order = new Order(UUID.randomUUID(), new Location(6, 6), 1);
        UUID id = order.getId();
        orderRepository.add(order);

        Optional<Order> loadedOrder = orderRepository.getById(id);

        assertAll(
                () -> assertTrue(loadedOrder.isPresent()),
                () -> assertEquals(order.getId(), loadedOrder.get().getId()),
                () -> assertEquals(OrderStatus.CREATED, loadedOrder.get().getStatus()),
                () -> assertEquals(order.getVolume(), loadedOrder.get().getVolume()),
                () -> assertEquals(order.getLocation(), loadedOrder.get().getLocation())
        );
    }

    @Test
    void shouldReturnAnyCreatedOrder() {
        Optional<Order> loadedOrder = orderRepository.getAnyCreated();
        assertFalse(loadedOrder.isPresent());

        Order order = new Order(UUID.randomUUID(), new Location(6, 6), 1);
        orderRepository.add(order);
        loadedOrder = orderRepository.getAnyCreated();
        assertTrue(loadedOrder.isPresent());
    }

    @Test
    void shouldReturnOnlyAssignedOrders() {
        List<Order> loadedOrder = orderRepository.getAssigned();
        assertTrue(loadedOrder.isEmpty());

        Order order = new Order(UUID.randomUUID(), new Location(6, 6), 1);
        var courierId = UUID.randomUUID();
        order.assign(courierId);
        orderRepository.add(order);
        loadedOrder = orderRepository.getAssigned();
        assertFalse(loadedOrder.isEmpty());
        assertEquals(courierId, loadedOrder.get(0).getCourierId());
    }
}