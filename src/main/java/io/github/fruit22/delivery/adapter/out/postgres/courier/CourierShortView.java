package io.github.fruit22.delivery.adapter.out.postgres.courier;

import io.github.fruit22.delivery.core.domain.model.Location;

import java.util.UUID;

public interface CourierShortView {

    UUID getId();

    String getName();

    Location getLocation();

}