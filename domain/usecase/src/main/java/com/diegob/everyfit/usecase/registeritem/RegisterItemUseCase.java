package com.diegob.everyfit.usecase.registeritem;

import com.diegob.everyfit.model.clothingitem.ClothingItem;
import com.diegob.everyfit.model.clothingitem.gateways.ClothingItemRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.util.function.Function;

@RequiredArgsConstructor
public class RegisterItemUseCase implements Function<ClothingItem, Mono<ClothingItem>> {

    private final ClothingItemRepository clothingItemRepository;


    @Override
    public Mono<ClothingItem> apply(ClothingItem clothingItem) {
        return clothingItemRepository.registerItem(clothingItem);
    }

}
