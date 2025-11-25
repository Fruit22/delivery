package io.github.fruit22.delivery.core.domain.model.courier;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.Objects;
import java.util.UUID;

@Getter
@EqualsAndHashCode(of = "id")
public class StoragePlace {

    private final UUID id;
    private final String name;
    private final int totalVolume;
    private UUID orderId;

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
