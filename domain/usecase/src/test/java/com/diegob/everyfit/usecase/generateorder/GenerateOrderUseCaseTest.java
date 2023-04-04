package com.diegob.everyfit.usecase.generateorder;

import com.diegob.everyfit.model.order.Order;
import com.diegob.everyfit.model.order.gateways.OrderRepository;
import com.diegob.everyfit.usecase.DataMocks;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class GenerateOrderUseCaseTest {

    @Mock
    OrderRepository orderRepository;

    GenerateOrderUseCase generateOrderUseCase;

    @BeforeEach
    void init() {
        generateOrderUseCase = new GenerateOrderUseCase(orderRepository);
    }

    @Test
    @DisplayName("generateOrder")
    void apply() {

        Mockito.when(orderRepository.createOrder(ArgumentMatchers.any(Order.class))).thenReturn(DataMocks.orderMono());

        var result = generateOrderUseCase.apply(DataMocks.rawOrder());

        StepVerifier.create(result)
                .expectNextMatches(order -> order.getCustomer().equals("customer-id"))
                .verifyComplete();

        Mockito.verify(orderRepository, Mockito.times(1)).createOrder(ArgumentMatchers.any(Order.class));

    }

    //Test for fail case trhows exception
}