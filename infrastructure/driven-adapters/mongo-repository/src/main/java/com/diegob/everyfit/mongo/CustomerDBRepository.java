package com.diegob.everyfit.mongo;

import com.diegob.everyfit.mongo.data.CustomerData;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface CustomerDBRepository extends ReactiveMongoRepository<CustomerData, String> {
}
