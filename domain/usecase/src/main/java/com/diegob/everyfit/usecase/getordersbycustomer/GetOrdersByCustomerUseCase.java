package com.diegob.everyfit.usecase.getordersbycustomer;

import com.diegob.everyfit.model.order.Order;
import com.diegob.everyfit.model.order.gateways.OrderRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;

import java.util.function.Function;

@RequiredArgsConstructor
public class GetOrdersByCustomerUseCase implements Function<String,Flux<Order>> {

    private final OrderRepository orderRepository;


    @Override
    public Flux<Order> apply(String CustomerId) {
        return orderRepository.getOrdersByCustomer(CustomerId);
    }
}
