package com.diegob.everyfit.model.customer.gateways;

import com.diegob.everyfit.model.customer.Customer;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CustomerRepository {

    Mono<Customer> createUser(Customer customer);
    Flux<Customer> getAllCustomers();
    Mono<Customer> getCustomerById(String customerId);
    Mono<Customer> getCustomerByEmail(String email);
    Mono<String> deleteCustomer(String customerId);
}
