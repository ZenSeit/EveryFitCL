package com.diegob.everyfit.usecase.modifiedquantity;

import com.diegob.everyfit.model.clothingitem.ClothingItem;
import com.diegob.everyfit.model.clothingitem.gateways.ClothingItemRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.util.function.BiFunction;

@RequiredArgsConstructor
public class ModifiedQuantityUseCase implements BiFunction<String, Integer, Mono<ClothingItem>>  {

    private final ClothingItemRepository clothingItemRepository;

    @Override
    public Mono<ClothingItem> apply(String itemId, Integer quantity) {
        return clothingItemRepository.modifiedQuantity(itemId,quantity);
    }

}
