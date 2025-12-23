package io.github.fruit22.delivery.core.application.command;

import io.github.fruit22.delivery.core.domain.model.Location;
import io.github.fruit22.delivery.core.domain.model.order.Order;
import io.github.fruit22.delivery.core.port.OrderRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CreateOrderUseCase {

    private final OrderRepository orderRepository;

    @Transactional
    public void createOrder(CreateOrderCommand command) {
        Objects.requireNonNull(command, "CreateOrderCommand cannot be null");
        Objects.requireNonNull(command.orderId(), "OrderId cannot be null");
        Objects.requireNonNull(command.street(), "Street cannot be null");
        if (command.volume() < 0) {
            throw new IllegalArgumentException("Volume cannot be negative");
        }

        var order = new Order(command.orderId(), getLocation(command.street()), command.volume());
        orderRepository.add(order);
    }

    //todo: use geo service
    private Location getLocation(String street) {
        return new Location(1, 1);
    }
}
