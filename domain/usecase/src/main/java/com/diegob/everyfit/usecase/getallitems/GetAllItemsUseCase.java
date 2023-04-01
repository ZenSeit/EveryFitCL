package com.diegob.everyfit.usecase.getallitems;

import com.diegob.everyfit.model.clothingitem.ClothingItem;
import com.diegob.everyfit.model.clothingitem.gateways.ClothingItemRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;

import java.util.function.Supplier;

@RequiredArgsConstructor
public class GetAllItemsUseCase implements Supplier<Flux<ClothingItem>> {

    private final ClothingItemRepository clothingItemRepository;


    @Override
    public Flux<ClothingItem> get() {
        return clothingItemRepository.getAllItems();
    }

}
