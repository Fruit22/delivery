package io.github.fruit22.delivery.core.application.command;

import io.github.fruit22.delivery.adapter.out.postgres.courier.CourierShortView;
import io.github.fruit22.delivery.adapter.out.postgres.courier.JpaCourierRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetAllCouriersUseCase {

    private final JpaCourierRepository jpaCourierRepository;

    public List<CourierShortView> getAllCouriers() {
        return jpaCourierRepository.findAllBy();
    }
}
