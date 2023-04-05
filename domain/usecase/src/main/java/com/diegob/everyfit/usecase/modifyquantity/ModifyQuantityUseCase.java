package com.diegob.everyfit.usecase.modifyquantity;

import com.diegob.everyfit.model.clothingitem.ClothingItem;
import com.diegob.everyfit.model.clothingitem.gateways.ClothingItemRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.util.function.BiFunction;

@RequiredArgsConstructor
public class ModifyQuantityUseCase implements BiFunction<String, Integer, Mono<ClothingItem>>  {

    private final ClothingItemRepository clothingItemRepository;

    @Override
    public Mono<ClothingItem> apply(String itemId, Integer quantity) {
        return clothingItemRepository.modifyQuantity(itemId,quantity);
    }

}
