package io.github.fruit22.delivery.adapter.out.postgres.courier;

import io.github.fruit22.delivery.adapter.out.postgres.BasePostgresContainerTest;
import io.github.fruit22.delivery.core.domain.model.Location;
import io.github.fruit22.delivery.core.domain.model.courier.Courier;
import io.github.fruit22.delivery.core.domain.model.order.Order;
import io.github.fruit22.delivery.core.port.CourierRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Import(CourierRepositoryImpl.class)
class CourierRepositoryImplTest extends BasePostgresContainerTest {

    @Autowired
    private CourierRepository courierRepository;


    @Test
    void shouldSaveAndFindCourierById() {
        Courier courier = new Courier(
                "Mark", 10, new Location(5, 5));
        var storagePlace = courier.getStoragePlaces().getFirst();

        courierRepository.add(courier);

        Optional<Courier> result = courierRepository.getById(courier.getId());
        assertTrue(result.isPresent());

        var loadedCourier = result.get();

        assertEquals(1, loadedCourier.getStoragePlaces().size());
        var loadedStoragePlace = loadedCourier.getStoragePlaces().getFirst();

        assertAll(
                () -> assertEquals(courier.getId(), loadedCourier.getId()),
                () -> assertEquals(courier.getName(), loadedCourier.getName()),
                () -> assertEquals(courier.getSpeed(), loadedCourier.getSpeed()),
                () -> assertEquals(courier.getLocation(), loadedCourier.getLocation()),
                () -> assertEquals(storagePlace.getId(), loadedStoragePlace.getId()),
                () -> assertEquals(storagePlace.getName(), loadedStoragePlace.getName()),
                () -> assertEquals(storagePlace.getOrderId(), loadedStoragePlace.getOrderId()),
                () -> assertEquals(storagePlace.getTotalVolume(), loadedStoragePlace.getTotalVolume()));
    }

    @Test
    void shouldReturnOnlyFreeCouriers() {
        Courier freeCourier = new Courier(
                "Mark", 10, new Location(5, 5));

        Order order = new Order(UUID.randomUUID(), new Location(6, 6), 1);
        Courier busyCourier = new Courier(
                "Oleg", 10, new Location(5, 5));
        busyCourier.takeOrder(order);

        courierRepository.add(freeCourier);
        courierRepository.add(busyCourier);


        List<Courier> freeCouriers = courierRepository.getFreeCouriers();

        assertEquals(1, freeCouriers.size());
        assertEquals(freeCourier.getId(), freeCouriers.get(0).getId());
    }

}