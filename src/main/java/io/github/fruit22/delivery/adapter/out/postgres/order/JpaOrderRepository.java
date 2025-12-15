package io.github.fruit22.delivery.adapter.out.postgres.order;

import io.github.fruit22.delivery.core.domain.model.order.Order;
import io.github.fruit22.delivery.core.domain.model.order.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface JpaOrderRepository extends JpaRepository<Order, UUID> {

    Optional<Order> findFirstByStatus(OrderStatus status);

    List<Order> findAllByStatus(OrderStatus status);
}
