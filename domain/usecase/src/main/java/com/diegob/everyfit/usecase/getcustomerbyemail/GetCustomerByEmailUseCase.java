package com.diegob.everyfit.usecase.getcustomerbyemail;

import com.diegob.everyfit.model.customer.Customer;
import com.diegob.everyfit.model.customer.gateways.CustomerRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.util.function.Function;

@RequiredArgsConstructor
public class GetCustomerByEmailUseCase implements Function<String, Mono<Customer>> {

    private final CustomerRepository customerRepository;

    @Override
    public Mono<Customer> apply(String email) {
        return customerRepository.getCustomerByEmail(email);
    }

}
