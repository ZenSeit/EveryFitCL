package com.diegob.everyfit.mongo;

import com.diegob.everyfit.model.clothingitem.ClothingItem;
import com.diegob.everyfit.mongo.data.ItemData;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;

public interface MongoDBRepository extends ReactiveMongoRepository<ItemData, String> {
}
