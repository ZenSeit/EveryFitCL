package com.diegob.everyfit.usecase.deletecustomer;

import com.diegob.everyfit.model.customer.gateways.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.CoreSubscriber;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class DeleteCustomerUseCaseTest {

    @Mock
    CustomerRepository customerRepository;

    DeleteCustomerUseCase deleteCustomerUseCase;

    @BeforeEach
    void init() {
        deleteCustomerUseCase = new DeleteCustomerUseCase(customerRepository);
    }

    @Test
    @DisplayName("deleteCustomer")
    void apply() {

        Mockito.when(customerRepository.deleteCustomer("1")).thenReturn(Mono.just("User deleted"));

        var result = deleteCustomerUseCase.apply("1");

        StepVerifier.create(result)
                .expectNextMatches(res -> res.equalsIgnoreCase("User deleted"))
                .verifyComplete();

    }

    @Test
    @DisplayName("deleteCustomerEmpty")
    void applyEmpty() {

        Mockito.when(customerRepository.deleteCustomer("1")).thenReturn(Mono.empty());

        var result = deleteCustomerUseCase.apply("1");

        StepVerifier.create(result)
                .expectNextCount(0)
                .verifyComplete();

    }
}