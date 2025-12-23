package io.github.fruit22.delivery.adapter.out.postgres.order;

import io.github.fruit22.delivery.core.domain.model.order.Order;
import io.github.fruit22.delivery.core.domain.model.order.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface JpaOrderRepository extends JpaRepository<Order, UUID> {

    Optional<Order> findFirstByStatus(OrderStatus status);

    List<Order> findAllByStatus(OrderStatus status);

    @Query(
            value = """
                        select
                            o.id as id,
                            o.location_x as location_x,
                            o.location_y as location_y
                        from delivery_order o
                        where o.status in ('CREATED', 'ASSIGNED')
                    """,
            nativeQuery = true
    )
    List<OrderShortView> findAllActiveOrders();
}
