package com.diegob.everyfit.api;

import com.diegob.everyfit.model.clothingitem.ClothingItem;
import com.diegob.everyfit.model.customer.Customer;
import com.diegob.everyfit.model.order.Order;
import com.diegob.everyfit.usecase.generateorder.GenerateOrderUseCase;
import com.diegob.everyfit.usecase.getallcustomers.GetAllCustomersUseCase;
import com.diegob.everyfit.usecase.getallitems.GetAllItemsUseCase;
import com.diegob.everyfit.usecase.getordersbycustomer.GetOrdersByCustomerUseCase;
import com.diegob.everyfit.usecase.registeritem.RegisterItemUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RouterRest {
    @Bean
    public RouterFunction<ServerResponse> getAllItems(GetAllItemsUseCase getAllItemsUseCase){
        return route(GET("/api/items"),
                request -> ServerResponse.status(200)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromPublisher(getAllItemsUseCase.get(), ClothingItem.class))
                        .onErrorResume(throwable -> ServerResponse.status(HttpStatus.NO_CONTENT).bodyValue(throwable.getMessage())));
    }

    @Bean
    public RouterFunction<ServerResponse> saveItem(RegisterItemUseCase registerItemUseCase){
        return route(POST("/api/items").and(accept(MediaType.APPLICATION_JSON)),
                request -> request.bodyToMono(ClothingItem.class)
                        .flatMap(item -> registerItemUseCase.apply(item)
                                .flatMap(result -> ServerResponse.status(201)
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .bodyValue(result))
                                .onErrorResume(throwable -> ServerResponse.status(HttpStatus.NOT_ACCEPTABLE).bodyValue(throwable.getMessage()))));
    }

    @Bean
    public RouterFunction<ServerResponse> getAllCustomers(GetAllCustomersUseCase getAllCustomersUseCase){
        return route(GET("/api/customers"),
                request -> ServerResponse.status(200)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromPublisher(getAllCustomersUseCase.get(), Customer.class))
                        .onErrorResume(throwable -> ServerResponse.status(HttpStatus.NO_CONTENT).bodyValue(throwable.getMessage())));
    }

    @Bean
    public RouterFunction<ServerResponse> getOrdersByCustomerID(GetOrdersByCustomerUseCase getOrdersByCustomerUseCase){
        return route(GET("/api/customers/{id}"),
                request -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromPublisher(getOrdersByCustomerUseCase.apply(request.pathVariable("id")), Order.class))
                        .onErrorResume(throwable -> ServerResponse.noContent().build()));

    }

    @Bean
    public RouterFunction<ServerResponse> generateOrder(GenerateOrderUseCase generateOrderUseCase){
        return route(POST("/api/orders").and(accept(MediaType.APPLICATION_JSON)),
                request -> request.bodyToMono(Order.class)
                        .flatMap(order -> generateOrderUseCase.apply(order)
                                .flatMap(result -> ServerResponse.status(201)
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .bodyValue(result))
                                .onErrorResume(throwable -> ServerResponse.status(HttpStatus.NOT_ACCEPTABLE).bodyValue(throwable.getMessage()))));
    }

}
