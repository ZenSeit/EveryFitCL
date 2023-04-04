package com.diegob.everyfit.usecase.getordersbycustomer;

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
class GetOrdersByCustomerUseCaseTest {

    @Mock
    OrderRepository orderRepository;

    GetOrdersByCustomerUseCase getOrdersByCustomerUseCase;

    @BeforeEach
    void init() {
        getOrdersByCustomerUseCase = new GetOrdersByCustomerUseCase(orderRepository);
    }

    @Test
    @DisplayName("getOrdersByCustomer")
    void apply() {

        Mockito.when(orderRepository.getOrdersByCustomer("customer-id")).thenReturn(DataMocks.ordersByCustomer());

        var result = getOrdersByCustomerUseCase.apply("customer-id");

        StepVerifier.create(result)
                .expectNextMatches(order -> order.getCustomer().equals("customer-id"))
                .expectNextMatches(order -> order.getCustomer().equals("customer-id"))
                .verifyComplete();

        Mockito.verify(orderRepository, Mockito.times(1)).getOrdersByCustomer("customer-id");

    }

    @Test
    @DisplayName("getOrdersByCustomerEmpty")
    void applyEmpty() {

        Mockito.when(orderRepository.getOrdersByCustomer("customer-id")).thenReturn(DataMocks.ordersByCustomerEmpty());

        var result = getOrdersByCustomerUseCase.apply("customer-id");

        StepVerifier.create(result)
                .expectNextCount(0)
                .verifyComplete();

        Mockito.verify(orderRepository, Mockito.times(1)).getOrdersByCustomer("customer-id");

    }

}