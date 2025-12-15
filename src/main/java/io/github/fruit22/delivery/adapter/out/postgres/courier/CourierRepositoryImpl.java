package io.github.fruit22.delivery.adapter.out.postgres.courier;

import io.github.fruit22.delivery.core.domain.model.courier.Courier;
import io.github.fruit22.delivery.core.domain.model.courier.StoragePlace;
import io.github.fruit22.delivery.core.port.CourierRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class CourierRepositoryImpl implements CourierRepository {

    private final JpaCourierRepository jpaCourierRepository;

    @Override
    public void add(Courier courier) {
        jpaCourierRepository.save(courier);
    }

    @Override
    public void update(Courier courier) {
        jpaCourierRepository.save(courier);
    }

    @Override
    public Optional<Courier> getById(UUID id) {
        return jpaCourierRepository.findById(id);
    }

    @Override
    public List<Courier> getFreeCouriers() {
        return jpaCourierRepository.findAll()
                .stream()
                .filter(courier -> courier.getStoragePlaces().stream().noneMatch((StoragePlace::isOccupied)))
                .toList();
    }
}
