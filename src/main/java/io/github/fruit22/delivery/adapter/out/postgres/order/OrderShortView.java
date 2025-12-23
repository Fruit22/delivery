package io.github.fruit22.delivery.adapter.out.postgres.order;


import java.util.UUID;

public interface OrderShortView {

    UUID getId();

    int getLocationX();

    int getLocationY();

}