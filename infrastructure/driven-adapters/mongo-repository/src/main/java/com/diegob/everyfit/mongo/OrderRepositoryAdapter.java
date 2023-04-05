package com.diegob.everyfit.mongo;

import com.diegob.everyfit.model.order.Order;
import com.diegob.everyfit.model.order.OrderState;
import com.diegob.everyfit.model.order.gateways.OrderRepository;
import com.diegob.everyfit.mongo.data.OrderData;
import lombok.RequiredArgsConstructor;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
@RequiredArgsConstructor
public class OrderRepositoryAdapter implements OrderRepository {

    private final ObjectMapper mapper;
    private final OrderDBRepository orderDBRepository;

    @Override
    public Mono<Order> createOrder(Order order) {
        return orderDBRepository
                .save(mapper.map(order, OrderData.class))
                .map(order1 -> mapper.map(order1,Order.class))
                .onErrorResume(Mono::error);
    }

    @Override
    public Flux<Order> getOrdersByCustomer(String CustomerId) {
        return orderDBRepository
                .findByCustomer(CustomerId)
                .switchIfEmpty(Mono.empty())
                .map(item -> mapper.map(item, Order.class))
                .onErrorResume(Mono::error);
    }

    @Override
    public Mono<Order> updateStateOrder(String orderId, int state) {
        return orderDBRepository
                .findById(orderId)
                .switchIfEmpty(Mono.error(new Throwable("Order not found")))
                .flatMap(order ->{
                    switch (state) {
                        case 0 -> order.setState(OrderState.PAYMENT);
                        case 1 -> order.setState(OrderState.DELIVERY);
                        case 2 -> order.setState(OrderState.COMPLETE);
                        case 3 -> order.setState(OrderState.CANCEL);
                        default -> {
                            return Mono.error(new Throwable("invalid state for order"));
                        }
                    }
                    return orderDBRepository.save(mapper.map(order,OrderData.class));
                }).map(order1 -> mapper.map(order1, Order.class));
    }

    @Override
    public Mono<String> deleteOrder(String OrderId) {
        return null;
    }

}
