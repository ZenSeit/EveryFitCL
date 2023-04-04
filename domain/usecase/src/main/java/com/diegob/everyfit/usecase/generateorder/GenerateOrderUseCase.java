package com.diegob.everyfit.usecase.generateorder;

import com.diegob.everyfit.model.clothingitem.ClothingItem;
import com.diegob.everyfit.model.order.Order;
import com.diegob.everyfit.model.order.gateways.OrderRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.util.function.Function;

@RequiredArgsConstructor
public class GenerateOrderUseCase implements Function<Order, Mono<Order>> {

    private final OrderRepository orderRepository;


    @Override
    public Mono<Order> apply(Order order) {
        return orderRepository.createOrder(order);
    }
}
