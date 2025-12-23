package io.github.fruit22.delivery.core.application.command;

import java.util.UUID;

public record CreateOrderCommand(UUID orderId, String street, int volume) {
}
