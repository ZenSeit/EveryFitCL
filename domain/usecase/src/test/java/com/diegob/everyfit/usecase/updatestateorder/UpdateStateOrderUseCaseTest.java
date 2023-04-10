package com.diegob.everyfit.usecase.updatestateorder;

import com.diegob.everyfit.model.order.OrderState;
import com.diegob.everyfit.model.order.gateways.OrderRepository;
import com.diegob.everyfit.usecase.DataMocks;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UpdateStateOrderUseCaseTest {

    @Mock
    OrderRepository orderRepository;

    UpdateStateOrderUseCase updateStateOrderUseCase;

    @BeforeEach
    void init() {
        updateStateOrderUseCase = new UpdateStateOrderUseCase(orderRepository);
    }

    @Test
    @DisplayName("updateStateOrder")
    void apply() {

        Mockito.when(orderRepository.updateStateOrder("orderId", 1)).thenReturn(
                DataMocks.orderMono()
        );

        var result = updateStateOrderUseCase.apply("orderId", 1);

        StepVerifier.create(result)
                .expectNextMatches(order -> order.getState() == OrderState.PAYMENT)
                .verifyComplete();

    }

    @Test
    @DisplayName("updateStateOrder_Fail")
    void apply_Fail() {

        Mockito.when(orderRepository.updateStateOrder("orderId", 1)).thenReturn(
                DataMocks.orderMono()
        );

        var result = updateStateOrderUseCase.apply("orderId", 1);

        StepVerifier.create(result)
                .expectNextMatches(order -> order.getState() != OrderState.CANCEL)
                .verifyComplete();

    }

}