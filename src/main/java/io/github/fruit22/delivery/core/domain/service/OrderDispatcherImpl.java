package io.github.fruit22.delivery.core.domain.service;

import io.github.fruit22.delivery.core.domain.model.courier.Courier;
import io.github.fruit22.delivery.core.domain.model.order.Order;
import io.github.fruit22.delivery.core.domain.model.order.OrderStatus;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class OrderDispatcherImpl implements OrderDispatcher {

    @Override
    public Optional<Courier> dispatch(Order order, List<Courier> couriers) {
        if (order.getStatus() != OrderStatus.CREATED) {
            throw new IllegalArgumentException("Order status must be CREATED");
        }
        return couriers.stream()
                .filter(courier -> courier.canTakeOrder(order))
                .min(Comparator.comparingDouble(courier -> courier.calculateTimeToLocation(order.getLocation())));
    }
}
