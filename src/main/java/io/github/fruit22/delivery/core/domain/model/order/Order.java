package io.github.fruit22.delivery.core.domain.model.order;

import io.github.fruit22.delivery.core.domain.model.Location;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.Objects;
import java.util.UUID;

@Getter
@EqualsAndHashCode(of = "id")
public class Order {

    private final UUID id;
    private final Location location;
    private final int volume;
    private OrderStatus status;
    private UUID courierId;

    public Order(UUID id, Location location, int volume) {
        this.id = Objects.requireNonNull(id, "Id cannot be null");
        this.location = Objects.requireNonNull(location, "Location cannot be null");
        if (volume <= 0) {
            throw new IllegalArgumentException("Volume cannot be negative or zero");
        }
        this.volume = volume;
        this.status = OrderStatus.CREATED;
    }

    public void assign(UUID courierId) {
        this.courierId = Objects.requireNonNull(courierId, "CourierId cannot be null");
        status = OrderStatus.ASSIGNED;
    }

    public void completed() {
        if (status != OrderStatus.ASSIGNED) {
            throw new IllegalStateException("Only assigned orders can be completed");
        }
        this.status = OrderStatus.COMPLETED;

    }

}
