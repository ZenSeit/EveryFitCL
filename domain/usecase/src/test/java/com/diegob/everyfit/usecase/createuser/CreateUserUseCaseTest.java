package com.diegob.everyfit.usecase.createuser;

import com.diegob.everyfit.model.customer.Customer;
import com.diegob.everyfit.model.customer.gateways.CustomerRepository;
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
class CreateUserUseCaseTest {

    @Mock
    CustomerRepository customerRepository;

    CreateUserUseCase createUserUseCase;

    @BeforeEach
    void init() {
        createUserUseCase = new CreateUserUseCase(customerRepository);
    }

    @Test
    @DisplayName("createUser")
    void apply() {

        Mockito.when(customerRepository.createUser(ArgumentMatchers.any(Customer.class))).thenReturn(DataMocks.customerById());

        var result = createUserUseCase.apply(DataMocks.customerRaw());

        StepVerifier.create(result)
                .expectNextMatches(customer -> customer.getId().equals("1"))
                .verifyComplete();

    }

    @Test
    @DisplayName("createUser_Fail")
    void apply_Fail() {

        Mockito.when(customerRepository.createUser(ArgumentMatchers.any(Customer.class))).thenReturn(DataMocks.customerByIdEmpty());

        var result = createUserUseCase.apply(DataMocks.customerRaw());

        StepVerifier.create(result)
                .expectNextCount(0)
                .verifyComplete();

    }

}