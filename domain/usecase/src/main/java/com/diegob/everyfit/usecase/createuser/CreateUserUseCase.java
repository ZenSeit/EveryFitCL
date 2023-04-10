package com.diegob.everyfit.usecase.createuser;

import com.diegob.everyfit.model.clothingitem.ClothingItem;
import com.diegob.everyfit.model.customer.Customer;
import com.diegob.everyfit.model.customer.gateways.CustomerRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.util.function.Function;

@RequiredArgsConstructor
public class CreateUserUseCase implements Function<Customer, Mono<Customer>> {

    private final CustomerRepository customerRepository;

    @Override
    public Mono<Customer> apply(Customer customer) {
        return customerRepository.createUser(customer);
    }
}
