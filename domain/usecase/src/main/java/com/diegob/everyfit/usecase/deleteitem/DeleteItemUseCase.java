package com.diegob.everyfit.usecase.deleteitem;

import com.diegob.everyfit.model.clothingitem.gateways.ClothingItemRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.util.function.Function;

@RequiredArgsConstructor
public class DeleteItemUseCase implements Function<String, Mono<String>> {

    private final ClothingItemRepository itemRepository;

    @Override
    public Mono<String> apply(String itemId) {
        return itemRepository.deleteItem(itemId);
    }
}
