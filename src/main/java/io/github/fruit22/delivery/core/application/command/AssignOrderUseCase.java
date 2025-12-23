package io.github.fruit22.delivery.core.application.command;

import io.github.fruit22.delivery.core.port.CourierRepository;
import io.github.fruit22.delivery.core.port.OrderRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;

@Service
@RequiredArgsConstructor
public class AssignOrderUseCase {

    private final OrderRepository orderRepository;
    private final CourierRepository courierRepository;

    @Transactional
    public void assignOrder() {
        orderRepository.getAnyCreated()
                .ifPresent(order -> {
                    courierRepository.getFreeCouriers()
                            .stream().min(Comparator.comparingDouble(c -> c.calculateTimeToLocation(order.getLocation())))
                            .ifPresent(courier -> {
                                order.assign(courier.getId());
                                orderRepository.update(order);
                                courier.takeOrder(order);
                                courierRepository.update(courier);
                            });
                });

    }
}
