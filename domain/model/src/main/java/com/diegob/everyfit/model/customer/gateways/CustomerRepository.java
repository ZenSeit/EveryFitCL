package com.diegob.everyfit.model.customer.gateways;

import com.diegob.everyfit.model.customer.Customer;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CustomerRepository {

    Mono<Customer> createUser();
    Flux<Customer> getAllCustomers();
    Mono<Customer> getCustomerById(String id);
    Mono<String> deleteCustomer(String id);
}
