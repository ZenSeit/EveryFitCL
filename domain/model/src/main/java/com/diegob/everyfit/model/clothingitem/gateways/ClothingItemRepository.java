package com.diegob.everyfit.model.clothingitem.gateways;

import com.diegob.everyfit.model.clothingitem.ClothingItem;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ClothingItemRepository {

    Flux<ClothingItem> getAllItems();
    Mono<ClothingItem> registerItem(ClothingItem item);
    Mono<ClothingItem> modifiedQuantity(String itemId,int quantity);
    Mono<ClothingItem> updateInventoryAfterBuy (String itemId,int quantity);
    Mono<ClothingItem> getItemById(String id);
    Mono<String> deleteItem(String id);

}
