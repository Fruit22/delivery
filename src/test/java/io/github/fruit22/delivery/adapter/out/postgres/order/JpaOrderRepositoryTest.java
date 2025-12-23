package io.github.fruit22.delivery.adapter.out.postgres.order;

import io.github.fruit22.delivery.adapter.out.postgres.BasePostgresContainerTest;
import io.github.fruit22.delivery.core.domain.model.Location;
import io.github.fruit22.delivery.core.domain.model.order.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
class JpaOrderRepositoryTest extends BasePostgresContainerTest {

    @Autowired
    private JpaOrderRepository repository;

    @Test
    void findAllActiveOrders() {
        Order first = new Order(UUID.randomUUID(), new Location(5, 5), 1);
        Order second = new Order(UUID.randomUUID(), new Location(6, 6), 1);
        second.assign(UUID.randomUUID());

        repository.saveAll(List.of(first, second));

        var result = repository.findAllActiveOrders();

        assertEquals(2, result.size());
    }
}