package com.diegob.everyfit.mongo;

import com.diegob.everyfit.model.clothingitem.ClothingItem;
import com.diegob.everyfit.model.clothingitem.gateways.ClothingItemRepository;
import com.diegob.everyfit.model.customer.Customer;
import com.diegob.everyfit.model.customer.gateways.CustomerRepository;
import com.diegob.everyfit.model.order.Order;
import com.diegob.everyfit.model.order.OrderState;
import com.diegob.everyfit.model.order.gateways.OrderRepository;
import com.diegob.everyfit.mongo.data.ItemData;
import com.diegob.everyfit.mongo.data.OrderData;
import lombok.RequiredArgsConstructor;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
@RequiredArgsConstructor
public class MongoRepositoryAdapter implements ClothingItemRepository,CustomerRepository,OrderRepository {

    private final ObjectMapper mapper;
    private final MongoDBRepository itemRepository;
    private final CustomerDBRepository customerDBRepository;
    private final OrderDBRepository orderDBRepository;

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
    public Mono<ClothingItem> modifiedQuantity(String itemId, int quantity) {
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
    public Mono<String> deleteItem(String id) {
        return null;
    }

    @Override
    public Flux<Customer> getAllCustomers() {
        return customerDBRepository
                .findAll()
                .switchIfEmpty(Mono.empty())
                .map(item -> mapper.map(item, Customer.class))
                .onErrorResume(Mono::error);
    }

    @Override
    public Mono<Customer> getCustomerById(String id) {
        return customerDBRepository
                .findById(id)
                .switchIfEmpty(Mono.empty())
                .map(item -> mapper.map(item, Customer.class))
                .onErrorResume(Mono::error);
    }

    @Override
    public Mono<String> deleteCustomer(String id) {
        return null;
    }

    @Override
    public Mono<Order> createOrder(Order order) {
        return orderDBRepository
                .save(mapper.map(order, OrderData.class))
                .map(order1 -> mapper.map(order1,Order.class))
                .onErrorResume(Mono::error);
    }

    @Override
    public Flux<Order> getOrdersByCustomer(String CustomerId) {
        return orderDBRepository
                .findByCustomer(CustomerId)
                .switchIfEmpty(Mono.empty())
                .map(item -> mapper.map(item, Order.class))
                .onErrorResume(Mono::error);
    }

    @Override
    public Mono<Order> updateStateOrder(String orderId, int state) {
        return orderDBRepository
                .findById(orderId)
                .switchIfEmpty(Mono.error(new Throwable("Order not found")))
                .flatMap(order ->{
                    switch (state) {
                        case 0 -> order.setState(OrderState.PAYMENT);
                        case 1 -> order.setState(OrderState.DELIVERY);
                        case 2 -> order.setState(OrderState.COMPLETE);
                        default -> {
                            return Mono.error(new Throwable("invalid state for order"));
                        }
                    }
                    return orderDBRepository.save(mapper.map(order,OrderData.class));
                }).map(order1 -> mapper.map(order1, Order.class));
    }

    @Override
    public Mono<String> deleteOrder(String OrderId) {
        return null;
    }
}
