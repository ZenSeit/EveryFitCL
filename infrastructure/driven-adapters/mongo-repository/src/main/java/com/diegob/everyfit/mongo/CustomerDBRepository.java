package com.diegob.everyfit.mongo;

import com.diegob.everyfit.mongo.data.CustomerData;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface CustomerDBRepository extends ReactiveMongoRepository<CustomerData, String> {

    Mono<CustomerData> findByEmail(String email);

}
