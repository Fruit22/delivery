package io.github.fruit22.delivery.core.domain.service;

import io.github.fruit22.delivery.core.domain.model.courier.Courier;
import io.github.fruit22.delivery.core.domain.model.order.Order;

import java.util.List;
import java.util.Optional;

public interface OrderDispatcher {

    Optional<Courier> dispatch(Order order, List<Courier> couriers);
}
