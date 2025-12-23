package io.github.fruit22.delivery.adapter.out.postgres.courier;

import io.github.fruit22.delivery.core.domain.model.courier.Courier;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface JpaCourierRepository extends JpaRepository<Courier, UUID> {

    List<CourierShortView> findAllBy();
}
