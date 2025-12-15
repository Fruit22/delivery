package io.github.fruit22.delivery.core.domain.model.courier;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;
import java.util.UUID;

@Entity
@Getter
@EqualsAndHashCode(of = "id")
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
public class StoragePlace {

    @Id
    @Column(name = "id", unique = true, nullable = false)
    private final UUID id;

    @Column(name = "name", nullable = false)
    private final String name;

    @Column(name = "totalVolume", nullable = false)
    private final int totalVolume;

    @Column(name = "orderId")
    private UUID orderId;

    @ManyToOne
    @JoinColumn(name = "courier_id")
    private Courier courier;

    public StoragePlace(String name, int totalVolume) {
        this.id = UUID.randomUUID();
        this.name = Objects.requireNonNull(name, "Name cannot be null");
        if (totalVolume <= 0) {
            throw new IllegalArgumentException("Total volume cannot be negative or zero");
        }
        this.totalVolume = totalVolume;
    }

    public boolean canStore(int volume) {
        return !isOccupied() && totalVolume >= volume;
    }

    public void store(UUID orderId, int volume) {
        if (isOccupied() || volume > totalVolume) {
            throw new IllegalStateException("Storage place is not empty or volume is too large");
        }
        this.orderId = orderId;
    }

    public void clear(UUID orderId) {
        if (this.orderId == orderId) {
            this.orderId = null;
        } else {
            throw new IllegalStateException("Order id: '" + orderId + "' is not in storage place");
        }
    }

    public boolean isOccupied() {
        return orderId != null;
    }

}
