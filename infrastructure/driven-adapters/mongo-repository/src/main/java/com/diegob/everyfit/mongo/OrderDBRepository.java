package com.diegob.everyfit.mongo;

import com.diegob.everyfit.model.order.Order;
import com.diegob.everyfit.mongo.data.OrderData;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface OrderDBRepository extends ReactiveMongoRepository<OrderData, String> {
    Flux<Order> findByCustomer(String customerId);
}
