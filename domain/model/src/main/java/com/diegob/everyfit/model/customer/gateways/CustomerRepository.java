package com.diegob.everyfit.model.customer.gateways;

import com.diegob.everyfit.model.customer.Customer;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CustomerRepository {

    Flux<Customer> getAllCustomer();
    Mono<Customer> getCustomerById(String id);
    Mono<String> deleteCustomer(String id);
}
