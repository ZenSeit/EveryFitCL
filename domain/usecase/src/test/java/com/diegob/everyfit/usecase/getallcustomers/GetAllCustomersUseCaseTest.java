package com.diegob.everyfit.usecase.getallcustomers;

import com.diegob.everyfit.model.customer.gateways.CustomerRepository;
import com.diegob.everyfit.usecase.DataMocks;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;


@ExtendWith(MockitoExtension.class)
class GetAllCustomersUseCaseTest {

    @Mock
    CustomerRepository customerRepository;

    GetAllCustomersUseCase getAllCustomersUseCase;

    @BeforeEach
    void init() {
        getAllCustomersUseCase = new GetAllCustomersUseCase(customerRepository);
    }

    @Test
    @DisplayName("getAllCustomers")
    void get() {

        Mockito.when(customerRepository.getAllCustomers()).thenReturn(DataMocks.allCustomer());

        var result = getAllCustomersUseCase.get();

        StepVerifier.create(result)
                .expectNextMatches(customer -> customer.getId().equals("1"))
                .expectNextMatches(customer -> customer.getId().equals("2"))
                .verifyComplete();

        Mockito.verify(customerRepository, Mockito.times(1)).getAllCustomers();
    }

    @Test
    @DisplayName("getAllCustomersEmpty")
    void getEmpty() {

        Mockito.when(customerRepository.getAllCustomers()).thenReturn(DataMocks.allCustomerEmpty());

        var result = getAllCustomersUseCase.get();

        StepVerifier.create(result)
                .expectNextCount(0)
                .verifyComplete();

        Mockito.verify(customerRepository, Mockito.times(1)).getAllCustomers();
    }

}