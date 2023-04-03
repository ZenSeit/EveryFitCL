package com.diegob.everyfit.usecase.getitembyid;

import com.diegob.everyfit.model.clothingitem.ClothingItem;
import com.diegob.everyfit.model.clothingitem.gateways.ClothingItemRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.util.function.Function;

@RequiredArgsConstructor
public class GetItemByIdUseCase implements Function<String, Mono<ClothingItem>> {

    private final ClothingItemRepository clothingItemRepository;

    @Override
    public Mono<ClothingItem> apply(String itemId) {
        return clothingItemRepository.getItemById(itemId);
    }
}
