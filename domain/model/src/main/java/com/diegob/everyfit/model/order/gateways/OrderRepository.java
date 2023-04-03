package com.diegob.everyfit.model.order.gateways;

import com.diegob.everyfit.model.order.Order;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface OrderRepository {

    Flux<Order> getOrdersByCustomer(String CustomerId);
    Mono<Order> updateStateOrder(String OrderId, int state);
    Mono<String> deleteOrder(String OrderId);

}
