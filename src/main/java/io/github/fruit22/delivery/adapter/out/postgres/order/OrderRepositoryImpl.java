package io.github.fruit22.delivery.adapter.out.postgres.order;

import io.github.fruit22.delivery.core.domain.model.order.Order;
import io.github.fruit22.delivery.core.domain.model.order.OrderStatus;
import io.github.fruit22.delivery.core.port.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class OrderRepositoryImpl implements OrderRepository {

    private final JpaOrderRepository jpaOrderRepository;

    @Override
    public void add(Order order) {
        jpaOrderRepository.save(order);
    }

    @Override
    public void update(Order order) {
        jpaOrderRepository.save(order);
    }

    @Override
    public Optional<Order> getById(UUID id) {
        return jpaOrderRepository.findById(id);
    }

    @Override
    public Optional<Order> getAnyCreated() {
        return jpaOrderRepository.findFirstByStatus(OrderStatus.CREATED);
    }

    @Override
    public List<Order> getAssigned() {
        return jpaOrderRepository.findAllByStatus(OrderStatus.ASSIGNED);
    }
}
