package com.diegob.everyfit.usecase.deleteorder;

import com.diegob.everyfit.model.order.gateways.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class DeleteOrderUseCaseTest {

    @Mock
    OrderRepository orderRepository;

    DeleteOrderUseCase deleteOrderUseCase;

    @BeforeEach
    void init() {
        deleteOrderUseCase = new DeleteOrderUseCase(orderRepository);
    }


    @Test
    @DisplayName("deleteOrder")
    void apply() {

        Mockito.when(orderRepository.deleteOrder("1")).thenReturn(Mono.just("Order deleted"));

        var result = deleteOrderUseCase.apply("1");

        StepVerifier.create(result)
                .expectNextMatches(res -> res.equalsIgnoreCase("Order deleted"))
                .verifyComplete();
    }

    @Test
    @DisplayName("deleteOrderEmpty")
    void applyEmpty() {

        Mockito.when(orderRepository.deleteOrder("1")).thenReturn(Mono.empty());

        var result = deleteOrderUseCase.apply("1");

        StepVerifier.create(result)
                .expectNextCount(0)
                .verifyComplete();
    }
}