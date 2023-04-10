package com.diegob.everyfit.mongo;

import com.diegob.everyfit.model.customer.Customer;
import com.diegob.everyfit.model.customer.gateways.CustomerRepository;
import com.diegob.everyfit.mongo.data.CustomerData;
import lombok.RequiredArgsConstructor;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
@RequiredArgsConstructor
public class CustomerRepositoryAdapter implements CustomerRepository {

    private final ObjectMapper mapper;

    private final CustomerDBRepository customerDBRepository;

    @Override
    public Mono<Customer> createUser(Customer customer) {
        return customerDBRepository
                .findByEmail(customer.getEmail())
                .switchIfEmpty(Mono.error(new Throwable("User already exists")))
                .flatMap(customer1 -> customerDBRepository.save(mapper.map(customer, CustomerData.class)))
                .map(item -> mapper.map(item, Customer.class));
    }

    @Override
    public Flux<Customer> getAllCustomers() {
        return customerDBRepository
                .findAll()
                .switchIfEmpty(Mono.empty())
                .map(item -> mapper.map(item, Customer.class))
                .onErrorResume(Mono::error);
    }

    @Override
    public Mono<Customer> getCustomerById(String id) {
        return customerDBRepository
                .findById(id)
                .switchIfEmpty(Mono.empty())
                .map(item -> mapper.map(item, Customer.class))
                .onErrorResume(Mono::error);
    }

    @Override
    public Mono<Customer> getCustomerByEmail(String email) {
        return customerDBRepository
                .findByEmail(email)
                .switchIfEmpty(Mono.empty())
                .map(item -> mapper.map(item, Customer.class))
                .onErrorResume(Mono::error);
    }

    @Override
    public Mono<String> deleteCustomer(String customerId) {
        return customerDBRepository
                .findById(customerId)
                .switchIfEmpty(Mono.error(new Throwable("User not found")))
                .flatMap(user -> customerDBRepository.delete(user).thenReturn("Customer deleted"));
    }

}
