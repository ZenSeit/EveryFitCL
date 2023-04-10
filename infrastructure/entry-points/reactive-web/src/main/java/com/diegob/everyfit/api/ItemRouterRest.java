package com.diegob.everyfit.api;

import com.diegob.everyfit.model.clothingitem.ClothingItem;
import com.diegob.everyfit.model.customer.Customer;
import com.diegob.everyfit.model.order.Order;
import com.diegob.everyfit.usecase.generateorder.GenerateOrderUseCase;
import com.diegob.everyfit.usecase.getallcustomers.GetAllCustomersUseCase;
import com.diegob.everyfit.usecase.getallitems.GetAllItemsUseCase;
import com.diegob.everyfit.usecase.getitembyid.GetItemByIdUseCase;
import com.diegob.everyfit.usecase.getordersbycustomer.GetOrdersByCustomerUseCase;
import com.diegob.everyfit.usecase.modifyquantity.ModifyQuantityUseCase;
import com.diegob.everyfit.usecase.registeritem.RegisterItemUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springdoc.core.annotations.RouterOperation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class ItemRouterRest {
    @Bean
    @RouterOperation(path = "/api/items",
            produces = {MediaType.APPLICATION_JSON_VALUE},
            beanClass = GetAllItemsUseCase.class,
            method = RequestMethod.GET,
            beanMethod = "get",
            operation = @Operation(operationId = "getAllItems",
                    tags = "Item use cases",
                    responses = {
                            @ApiResponse(responseCode = "200",
                                    description = "Success",
                                    content = @Content(schema = @Schema(implementation = ClothingItem.class))),
                            @ApiResponse(responseCode = "204", description = "No customers found")}
            )
    )
    public RouterFunction<ServerResponse> getAllItems(GetAllItemsUseCase getAllItemsUseCase){
        return route(GET("/api/items"),
                request -> ServerResponse.status(200)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromPublisher(getAllItemsUseCase.get(), ClothingItem.class))
                        .onErrorResume(throwable -> ServerResponse.status(HttpStatus.NO_CONTENT).bodyValue(throwable.getMessage())));
    }

    @Bean
    @RouterOperation(path = "/api/items",
            produces = {MediaType.APPLICATION_JSON_VALUE},
            beanClass = RegisterItemUseCase.class,
            method = RequestMethod.POST,
            beanMethod = "apply",
            operation = @Operation(
                    operationId = "saveItem",
                    tags = "Item use cases",
                    parameters = {
                            @Parameter(
                                    name = "clothingItem",
                                    in = ParameterIn.PATH,
                                    schema = @Schema(implementation = ClothingItem.class)
                            )
                    },
                    responses = {
                            @ApiResponse(
                                    responseCode = "201",
                                    description = "Success",
                                    content = @Content(schema = @Schema(implementation = ClothingItem.class))
                            ),
                            @ApiResponse(responseCode = "406", description = "Not acceptable")},
                    requestBody = @RequestBody(required = true,
                            description = "Save a customer",
                            content = @Content(schema = @Schema(implementation = ClothingItem.class)))
            )
    )
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
    @RouterOperation(path = "/api/items/{itemId}/{quantity}",
            produces = {MediaType.APPLICATION_JSON_VALUE},
            beanClass = ModifyQuantityUseCase.class,
            method = RequestMethod.PUT,
            beanMethod = "apply",
            operation = @Operation(
                    operationId = "modifyQuantityItem",
                    tags = "Item use cases",
                    parameters = {
                            @Parameter(
                                    name = "itemId",
                                    description = "Item Id",
                                    required = true,
                                    in = ParameterIn.PATH
                            ),
                            @Parameter(
                                    name = "quantity",
                                    description = "Units in stock for the item",
                                    required = true,
                                    in = ParameterIn.PATH
                            )
                    },
                    responses = {
                            @ApiResponse(
                                    responseCode = "201",
                                    description = "Success",
                                    content = @Content(schema = @Schema(implementation = ClothingItem.class))
                            ),
                            @ApiResponse(responseCode = "406", description = "Not acceptable")}
            )
    )
    public RouterFunction<ServerResponse> modifyQuantityItem(ModifyQuantityUseCase modifyQuantityUseCase){
        return route(PUT("/api/items/{itemId}/{quantity}"),
                request -> modifyQuantityUseCase.apply(request.pathVariable("itemId"),
                                Integer.parseInt(request.pathVariable("quantity")))
                        .flatMap(item ->ServerResponse.ok()
                                .contentType(MediaType.APPLICATION_JSON)
                                        .bodyValue(item))
                                .onErrorResume(throwable -> ServerResponse.status(HttpStatus.NOT_ACCEPTABLE).bodyValue(throwable.getMessage())));
    }

    @Bean
    @RouterOperation(path = "/api/items/{itemId}",
            produces = {MediaType.APPLICATION_JSON_VALUE},
            beanClass = GetItemByIdUseCase.class,
            method = RequestMethod.GET,
            beanMethod = "apply",
            operation = @Operation(operationId = "getItemById",
                    tags = "Item use cases",
                    parameters = {
                            @Parameter(
                                    name = "itemId",
                                    description = "item id",
                                    required = true,
                                    in = ParameterIn.PATH
                            )
                    },
                    responses = {
                            @ApiResponse(
                                    responseCode = "200",
                                    description = "Item found",
                                    content = @Content(
                                            schema = @Schema(
                                                    implementation = ClothingItem.class
                                            )
                                    )
                            ),
                            @ApiResponse(responseCode = "404",
                                    description = "Item not found"
                            )
                    }
            )
    )
    public RouterFunction<ServerResponse> getItemById(GetItemByIdUseCase getItemByIdUseCase){
        return route(GET("/api/items/{itemId}"),
                request -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromPublisher(getItemByIdUseCase.apply(request.pathVariable("itemId")), ClothingItem.class))
                        .onErrorResume(throwable -> ServerResponse.noContent().build()));

    }



}
