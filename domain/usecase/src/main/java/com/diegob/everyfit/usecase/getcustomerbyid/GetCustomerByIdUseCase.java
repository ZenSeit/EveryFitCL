package com.diegob.everyfit.usecase.getcustomerbyid;

import com.diegob.everyfit.model.clothingitem.ClothingItem;
import com.diegob.everyfit.model.clothingitem.gateways.ClothingItemRepository;
import com.diegob.everyfit.model.customer.Customer;
import com.diegob.everyfit.model.customer.gateways.CustomerRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.function.Function;

@RequiredArgsConstructor
public class GetCustomerByIdUseCase implements Function<String, Mono<Customer>> {

    private final CustomerRepository customerRepository;


    @Override
    public Mono<Customer> apply(String customerId) {
        return customerRepository.getCustomerById(customerId);
    }
}
