package io.github.fruit22.delivery.core.domain.model.courier;

import io.github.fruit22.delivery.core.domain.model.Location;
import io.github.fruit22.delivery.core.domain.model.order.Order;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Getter
@EqualsAndHashCode(of = "id")
public class Courier {

    private final UUID id;
    private final String name;
    private final int speed;

    private final List<StoragePlace> storagePlaces = new ArrayList<>();
    private Location location;

    public Courier(String name, int speed, Location location) {
        this.id = UUID.randomUUID();
        this.name = Objects.requireNonNull(name, "Name cannot be null");
        this.location = Objects.requireNonNull(location, "Location cannot be null");
        if (speed <= 0) {
            throw new IllegalArgumentException("Speed must be greater than zero");
        }
        this.speed = speed;
        this.storagePlaces.add(new StoragePlace("BAG", 10));
    }

    public void addStoragePlace(String name, int volume) {
        this.storagePlaces.add(new StoragePlace(name, volume));
    }

    public boolean canTakeOrder(Order order) {
        Objects.requireNonNull(order, "Order cannot be null");
        return this.storagePlaces.stream().anyMatch(
                storagePlace -> storagePlace.canStore(order.getVolume()));
    }

    public void takeOrder(Order order) {
        Objects.requireNonNull(order, "Order cannot be null");
        storagePlaces.stream()
                .filter(storagePlace -> storagePlace.canStore(order.getVolume()))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException(String.format("Cannot take order %s", order.getId())))
                .store(order.getId(), order.getVolume());
    }

    public void completeOrder(Order order) {
        Objects.requireNonNull(order, "Order cannot be null");
        this.storagePlaces.stream()
                .filter(StoragePlace::isOccupied)
                .filter(storagePlace -> storagePlace.getOrderId().equals(order.getId()))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException(String.format("Cannot complete order %s", order.getId())))
                .clear(order.getId());
    }

    public double calculateTimeToLocation(Location location) {
        Objects.requireNonNull(location, "Location cannot be null");
        return (double) this.location.calculateRoute(location) / speed;
    }

    public void move(Location target) {
        Objects.requireNonNull(target, "Target location cannot be null");
        int difX = target.x() - location.x();
        int difY = target.y() - location.y();
        int cruisingRange = speed;
        int moveX = Math.max(-cruisingRange, Math.min(difX, cruisingRange));
        cruisingRange = cruisingRange - Math.abs(moveX);
        int moveY = Math.max(-cruisingRange, Math.min(difY, cruisingRange));
        location = new Location(location.x() + moveX, location.y() + moveY);
    }

    public List<StoragePlace> getStoragePlaces() {
        return List.copyOf(storagePlaces);
    }

}
