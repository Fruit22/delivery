package io.github.fruit22.delivery.core.application.command;

import io.github.fruit22.delivery.adapter.out.postgres.order.JpaOrderRepository;
import io.github.fruit22.delivery.adapter.out.postgres.order.OrderShortView;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@RequiredArgsConstructor
public class GetAllNotCompletedOrdersUseCase {

    private final JpaOrderRepository repository;

    public Collection<OrderShortView> getAllActiveOrders() {
        return repository.findAllActiveOrders();

    }
}
