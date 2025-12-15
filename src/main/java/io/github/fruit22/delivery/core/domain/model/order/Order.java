package io.github.fruit22.delivery.core.domain.model.order;

import io.github.fruit22.delivery.core.domain.model.Location;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "delivery_order")
@Getter
@EqualsAndHashCode(of = "id")
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
public class Order {

    @Id
    @Column(name = "id", nullable = false, updatable = false)
    private final UUID id;

    @Embedded
    private final Location location;

    @Column(name = "volume", nullable = false, updatable = false)
    private final int volume;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private OrderStatus status;

    @Column(name = "courier_id")
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
