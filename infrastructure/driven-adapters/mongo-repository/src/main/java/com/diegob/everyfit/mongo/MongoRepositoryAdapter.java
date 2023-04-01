package com.diegob.everyfit.mongo;

import com.diegob.everyfit.model.clothingitem.ClothingItem;
import com.diegob.everyfit.model.clothingitem.gateways.ClothingItemRepository;
import com.diegob.everyfit.model.customer.Customer;
import com.diegob.everyfit.model.customer.gateways.CustomerRepository;
import com.diegob.everyfit.model.order.Order;
import com.diegob.everyfit.model.order.gateways.OrderRepository;
import com.diegob.everyfit.mongo.data.ItemData;
import com.diegob.everyfit.mongo.helper.AdapterOperations;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
@RequiredArgsConstructor
public class MongoRepositoryAdapter implements ClothingItemRepository,CustomerRepository {

    private final ObjectMapper mapper;
    private final MongoDBRepository repository;
    private final CustomerDBRepository customerDBRepository;

    @Override
    public Flux<ClothingItem> getAllItems() {
        return repository
                .findAll()
                .switchIfEmpty(Mono.error(new Throwable("No items available")))
                .map(student -> mapper.map(student, ClothingItem.class))
                .onErrorResume(Mono::error);
    }

    @Override
    public Mono<ClothingItem> registerItem(ClothingItem item) {
        return repository
                .save(mapper.map(item, ItemData.class))
                .map(item1 -> mapper.map(item1, ClothingItem.class))
                .onErrorResume(Mono::error);
    }

    @Override
    public Mono<ClothingItem> modifiedQuantity(String itemId, int quantity) {
        return null;
    }

    @Override
    public Mono<ClothingItem> updateInventoryAfterBuy(String itemId, int quantity) {
        return null;
    }

    @Override
    public Mono<ClothingItem> getItemById(String id) {
        return null;
    }

    @Override
    public Mono<String> deleteItem(String id) {
        return null;
    }

    @Override
    public Flux<Customer> getAllCustomers() {
        return customerDBRepository
                .findAll()
                .switchIfEmpty(Mono.empty())
                .map(student -> mapper.map(student, Customer.class))
                .onErrorResume(Mono::error);
    }

    @Override
    public Mono<Customer> getCustomerById(String id) {
        return null;
    }

    @Override
    public Mono<String> deleteCustomer(String id) {
        return null;
    }
}
