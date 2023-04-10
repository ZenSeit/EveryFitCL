package com.diegob.everyfit.mongo;

import com.diegob.everyfit.model.clothingitem.ClothingItem;
import com.diegob.everyfit.model.order.Order;
import com.diegob.everyfit.model.order.OrderState;
import com.diegob.everyfit.model.order.gateways.OrderRepository;
import com.diegob.everyfit.mongo.data.OrderData;
import lombok.RequiredArgsConstructor;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrderRepositoryAdapter implements OrderRepository {

    private final ObjectMapper mapper;
    private final OrderDBRepository orderDBRepository;
    private final MongoDBRepository itemRepository;

    @Override
    public Mono<Order> createOrder(Order order) {
        List<ClothingItem> orderItems = order.getProducts();
        return itemRepository.findAllById(orderItems.stream().map(ClothingItem::getId).toList())
                .switchIfEmpty(Mono.error(new Throwable("No items available")))
                .map(item -> {
                    System.out.println(item.getQuantity());
                    System.out.println(orderItems.stream().filter(item1 -> item1.getId().equals(item.getId())).findFirst().get().getQuantity());
                    if(item.getQuantity()>=orderItems.stream().filter(item1 -> item1.getId().equals(item.getId())).findFirst().get().getQuantity()){
                        item.setQuantity(item.getQuantity()-orderItems.stream().filter(item1 -> item1.getId().equals(item.getId())).findFirst().get().getQuantity());
                        System.out.println(item);
                        return itemRepository.save(item);
                    }
                    return Mono.error(new Throwable("Item not have stock"));
                }).then(orderDBRepository
                .save(mapper.map(order, OrderData.class))
                .map(order1 -> mapper.map(order1,Order.class))
                .onErrorResume(Mono::error));
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
                        case 3 -> order.setState(OrderState.CANCEL);
                        default -> {
                            return Mono.error(new Throwable("invalid state for order"));
                        }
                    }
                    return orderDBRepository.save(mapper.map(order,OrderData.class));
                }).map(order1 -> mapper.map(order1, Order.class));
    }

    @Override
    public Mono<String> deleteOrder(String orderId) {
        return orderDBRepository
                .findById(orderId)
                .switchIfEmpty(Mono.error(new Throwable("Order not found")))
                .flatMap(order -> orderDBRepository.delete(order).then(Mono.just("Order deleted")));
    }

}
