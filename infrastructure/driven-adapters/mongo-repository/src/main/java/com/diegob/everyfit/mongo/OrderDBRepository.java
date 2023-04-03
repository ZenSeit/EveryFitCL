package com.diegob.everyfit.mongo;

import com.diegob.everyfit.mongo.data.OrderData;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface OrderDBRepository extends ReactiveMongoRepository<OrderData, String> {
}
