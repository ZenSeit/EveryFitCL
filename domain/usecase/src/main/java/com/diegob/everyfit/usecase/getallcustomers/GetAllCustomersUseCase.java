package com.diegob.everyfit.usecase.getallcustomers;

import com.diegob.everyfit.model.customer.Customer;
import com.diegob.everyfit.model.customer.gateways.CustomerRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;

import java.util.function.Supplier;

@RequiredArgsConstructor
public class GetAllCustomersUseCase implements Supplier<Flux<Customer>> {

    private final CustomerRepository customerRepository;
    @Override
    public Flux<Customer> get() {
        return customerRepository.getAllCustomers();
    }
}
