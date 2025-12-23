package io.github.fruit22.delivery.core.application.command;

import io.github.fruit22.delivery.core.port.CourierRepository;
import io.github.fruit22.delivery.core.port.OrderRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MoveCourierUseCase {

    private final OrderRepository orderRepository;
    private final CourierRepository courierRepository;

    @Transactional
    public void move() {
        orderRepository.getAssigned().forEach(order -> {
            courierRepository.getById(order.getId())
                    .ifPresent(courier -> {
                        courier.move(order.getLocation());
                        if (courier.getLocation().equals(order.getLocation())) {
                            courier.completeOrder(order);
                        }
                        courierRepository.update(courier);
                    });
        });
    }
}
