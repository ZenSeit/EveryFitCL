package com.diegob.everyfit.usecase.getcustomerbyemail;

import com.diegob.everyfit.model.customer.gateways.CustomerRepository;
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
class GetCustomerByEmailUseCaseTest {

    @Mock
    CustomerRepository customerRepository;

    GetCustomerByEmailUseCase getCustomerByEmailUseCase;

    @BeforeEach
    void init() {
        getCustomerByEmailUseCase = new GetCustomerByEmailUseCase(customerRepository);
    }

    @Test
    @DisplayName("getCustomerByEmail")
    void apply() {

        Mockito.when(customerRepository.getCustomerByEmail("email")).thenReturn(DataMocks.customerById());

        var result = getCustomerByEmailUseCase.apply("email");

        StepVerifier.create(result)
                .expectNextMatches(customer -> customer.getEmail().equals("diego@correo.com"))
                .verifyComplete();

        Mockito.verify(customerRepository, Mockito.times(1)).getCustomerByEmail("email");

    }

    @Test
    @DisplayName("getCustomerByEmailEmpty")
    void applyEmpty() {

        Mockito.when(customerRepository.getCustomerByEmail("email")).thenReturn(DataMocks.customerByIdEmpty());

        var result = getCustomerByEmailUseCase.apply("email");

        StepVerifier.create(result)
                .expectNextCount(0)
                .verifyComplete();

        Mockito.verify(customerRepository, Mockito.times(1)).getCustomerByEmail("email");

    }
}