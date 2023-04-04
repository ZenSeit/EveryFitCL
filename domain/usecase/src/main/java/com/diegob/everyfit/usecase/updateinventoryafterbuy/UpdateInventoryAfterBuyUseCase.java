package com.diegob.everyfit.usecase.updateinventoryafterbuy;

import com.diegob.everyfit.model.clothingitem.ClothingItem;
import com.diegob.everyfit.model.clothingitem.gateways.ClothingItemRepository;
import com.diegob.everyfit.model.order.Order;
import com.diegob.everyfit.model.order.gateways.OrderRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.util.function.BiFunction;

@RequiredArgsConstructor
public class UpdateInventoryAfterBuyUseCase implements BiFunction<String, Integer, Mono<ClothingItem>> {

    private final ClothingItemRepository clothingItemRepository;

    @Override
    public Mono<ClothingItem> apply(String itemId, Integer quantity) {
        return clothingItemRepository.updateInventoryAfterBuy(itemId,quantity);
    }

}
