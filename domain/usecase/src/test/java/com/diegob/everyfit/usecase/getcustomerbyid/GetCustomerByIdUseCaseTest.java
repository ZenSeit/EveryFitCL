package com.diegob.everyfit.usecase.getcustomerbyid;

import com.diegob.everyfit.model.customer.gateways.CustomerRepository;
import com.diegob.everyfit.usecase.DataMocks;
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
class GetCustomerByIdUseCaseTest {

    @Mock
    CustomerRepository customerRepository;

    GetCustomerByIdUseCase getCustomerByIdUseCase;

    @BeforeEach
    void init() {
        getCustomerByIdUseCase = new GetCustomerByIdUseCase(customerRepository);
    }

    @Test
    @DisplayName("getCustomerById")
    void apply() {

        Mockito.when(customerRepository.getCustomerById("1")).thenReturn(DataMocks.customerById());

        var result = getCustomerByIdUseCase.apply("1");

        StepVerifier.create(result)
                .expectNextMatches(customer -> customer.getId().equals("1"))
                .verifyComplete();

        Mockito.verify(customerRepository, Mockito.times(1)).getCustomerById("1");

    }

    @Test
    @DisplayName("getCustomerByIdEmpty")
    void applyEmpty() {

        Mockito.when(customerRepository.getCustomerById("1")).thenReturn(DataMocks.customerByIdEmpty());

        var result = getCustomerByIdUseCase.apply("1");

        StepVerifier.create(result)
                .expectNextCount(0)
                .verifyComplete();

        Mockito.verify(customerRepository, Mockito.times(1)).getCustomerById("1");

    }
}