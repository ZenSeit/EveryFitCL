package com.diegob.everyfit.usecase.updatestateorder;

import com.diegob.everyfit.model.order.Order;
import com.diegob.everyfit.model.order.gateways.OrderRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.util.function.BiFunction;


@RequiredArgsConstructor
public class UpdateStateOrderUseCase implements BiFunction<String, Integer, Mono<Order>> {

    private final OrderRepository orderRepository;


    @Override
    public Mono<Order> apply(String orderId, Integer state) {
        return orderRepository.updateStateOrder(orderId,state);
    }
}
