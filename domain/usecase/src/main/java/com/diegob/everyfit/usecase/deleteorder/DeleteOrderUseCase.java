package com.diegob.everyfit.usecase.deleteorder;

import com.diegob.everyfit.model.order.gateways.OrderRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.util.function.Function;

@RequiredArgsConstructor
public class DeleteOrderUseCase implements Function<String, Mono<String>> {

    private final OrderRepository orderRepository;

    @Override
    public Mono<String> apply(String orderId) {
        return orderRepository.deleteOrder(orderId);
    }
}
