package com.diegob.everyfit.usecase.deletecustomer;

import com.diegob.everyfit.model.customer.gateways.CustomerRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.util.function.Function;

@RequiredArgsConstructor
public class DeleteCustomerUseCase implements Function<String, Mono<String>> {

    private final CustomerRepository customerRepository;

    @Override
    public Mono<String> apply(String customerId) {
        return customerRepository.deleteCustomer(customerId);
    }
}
