package com.diegob.everyfit.mongo;

import com.diegob.everyfit.model.clothingitem.ClothingItem;
import com.diegob.everyfit.model.clothingitem.gateways.ClothingItemRepository;
import com.diegob.everyfit.mongo.data.ItemData;
import lombok.RequiredArgsConstructor;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
@RequiredArgsConstructor
public class ItemRepositoryAdapter implements ClothingItemRepository {

    private final ObjectMapper mapper;
    private final MongoDBRepository itemRepository;


    @Override
    public Flux<ClothingItem> getAllItems() {
        return itemRepository
                .findAll()
                .switchIfEmpty(Mono.error(new Throwable("No items available")))
                .map(student -> mapper.map(student, ClothingItem.class))
                .onErrorResume(Mono::error);
    }

    @Override
    public Mono<ClothingItem> registerItem(ClothingItem item) {
        return itemRepository
                .save(mapper.map(item, ItemData.class))
                .map(item1 -> mapper.map(item1, ClothingItem.class))
                .onErrorResume(Mono::error);
    }

    @Override
    public Mono<ClothingItem> modifyQuantity(String itemId, int quantity) {
        return itemRepository
                .findById(itemId)
                .switchIfEmpty(Mono.empty())
                .flatMap(item ->{
                    if(quantity>=0){
                        item.setQuantity(quantity);
                        return itemRepository.save(item);
                    }
                    return Mono.just(item);
                }).map(item1 -> mapper.map(item1, ClothingItem.class));
    }

    @Override
    public Mono<ClothingItem> updateInventoryAfterBuy(String itemId, int quantity) {
        return itemRepository
                .findById(itemId)
                .switchIfEmpty(Mono.empty())
                .flatMap(item ->{
                    if(item.getQuantity()>=quantity){
                        item.setQuantity(item.getQuantity()-quantity);
                        return itemRepository.save(item);
                    }
                    return Mono.error(new Throwable("There is no enough stock"));
                }).map(item1 -> mapper.map(item1, ClothingItem.class));
    }

    @Override
    public Mono<ClothingItem> getItemById(String id) {
        return itemRepository
                .findById(id)
                .switchIfEmpty(Mono.error(new Throwable("Item not found")))
                .map(car -> mapper.map(car, ClothingItem.class))
                .onErrorResume(Mono::error);
    }

    @Override
    public Mono<String> deleteItem(String itemId) {
        return itemRepository
                .findById(itemId)
                .switchIfEmpty(Mono.error(new Throwable("Item not found")))
                .flatMap(item -> itemRepository.delete(item).thenReturn("Item deleted"));
    }

}
