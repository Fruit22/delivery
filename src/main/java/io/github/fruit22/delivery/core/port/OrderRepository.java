package io.github.fruit22.delivery.core.port;

import io.github.fruit22.delivery.core.domain.model.order.Order;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OrderRepository {

    void add(Order order);

    void update(Order order);

    Optional<Order> getById(UUID id);

    Optional<Order> getAnyCreated();

    List<Order> getAssigned();


}
