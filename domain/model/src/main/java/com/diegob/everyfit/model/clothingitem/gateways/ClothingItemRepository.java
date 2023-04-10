package com.diegob.everyfit.model.clothingitem.gateways;

import com.diegob.everyfit.model.clothingitem.ClothingItem;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ClothingItemRepository {

    Flux<ClothingItem> getAllItems();
    Mono<ClothingItem> registerItem(ClothingItem item);
    Mono<ClothingItem> modifyQuantity(String itemId,int quantity);
    Mono<ClothingItem> updateInventoryAfterBuy (String itemId,int quantity);
    Mono<ClothingItem> getItemById(String itemId);
    Mono<String> deleteItem(String itemId);

}
