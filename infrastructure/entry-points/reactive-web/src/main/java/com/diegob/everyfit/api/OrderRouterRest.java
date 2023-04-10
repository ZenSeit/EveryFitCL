package com.diegob.everyfit.api;

import com.diegob.everyfit.model.order.Order;
import com.diegob.everyfit.usecase.deleteorder.DeleteOrderUseCase;
import com.diegob.everyfit.usecase.generateorder.GenerateOrderUseCase;
import com.diegob.everyfit.usecase.getordersbycustomer.GetOrdersByCustomerUseCase;
import com.diegob.everyfit.usecase.updatestateorder.UpdateStateOrderUseCase;
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
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class OrderRouterRest {

    @Bean
    @RouterOperation(path = "/api/orders/{customerId}",
            produces = {MediaType.APPLICATION_JSON_VALUE},
            beanClass = GetOrdersByCustomerUseCase.class,
            method = RequestMethod.GET,
            beanMethod = "apply",
            operation = @Operation(operationId = "getOrdersByCustomerID",
                    tags = "Order use cases",
                    parameters = {
                            @Parameter(
                                    name = "customerId",
                                    description = "customer id",
                                    required = true,
                                    in = ParameterIn.PATH
                            )
                    },
                    responses = {
                            @ApiResponse(
                                    responseCode = "200",
                                    description = "Orders found",
                                    content = @Content(
                                            schema = @Schema(
                                                    implementation = Order.class
                                            )
                                    )
                            ),
                            @ApiResponse(responseCode = "404",
                                    description = "Orders not found"
                            )
                    }
            )
    )
    public RouterFunction<ServerResponse> getOrdersByCustomerID(GetOrdersByCustomerUseCase getOrdersByCustomerUseCase){
        return route(GET("/api/orders/{customerId}"),
                request -> getOrdersByCustomerUseCase.apply(request.pathVariable("customerId"))
                        .collectList()
                        .flatMap(orders -> ServerResponse.ok().body(BodyInserters.fromValue(orders)))
                        .switchIfEmpty(ServerResponse.notFound().build()));

    }

    @Bean
    @RouterOperation(path = "/api/orders",
            produces = {MediaType.APPLICATION_JSON_VALUE},
            beanClass = GenerateOrderUseCase.class,
            method = RequestMethod.POST,
            beanMethod = "apply",
            operation = @Operation(
                    operationId = "generateOrder",
                    tags = "Order use cases",
                    parameters = {
                            @Parameter(
                                    name = "order",
                                    in = ParameterIn.PATH,
                                    schema = @Schema(implementation = Order.class)
                            )
                    },
                    responses = {
                            @ApiResponse(
                                    responseCode = "201",
                                    description = "Success",
                                    content = @Content(schema = @Schema(implementation = Order.class))
                            ),
                            @ApiResponse(responseCode = "406", description = "Not acceptable")},
                    requestBody = @RequestBody(required = true,
                            description = "generate a new order",
                            content = @Content(schema = @Schema(implementation = Order.class)))
            )
    )
    public RouterFunction<ServerResponse> generateOrder(GenerateOrderUseCase generateOrderUseCase){
        return route(POST("/api/orders").and(accept(MediaType.APPLICATION_JSON)),
                request -> request.bodyToMono(Order.class)
                        .flatMap(order ->
                                generateOrderUseCase.apply(order)
                                        .flatMap(result -> ServerResponse.status(201)
                                                .contentType(MediaType.APPLICATION_JSON)
                                                .bodyValue(result))
                                        .onErrorResume(throwable -> ServerResponse.status(HttpStatus.NOT_ACCEPTABLE).bodyValue(throwable.getMessage()))
                        ));
    }

    @Bean
    @RouterOperation(path = "/api/orders/{orderId}",
            produces = {MediaType.APPLICATION_JSON_VALUE},
            beanClass = DeleteOrderUseCase.class,
            method = RequestMethod.DELETE,
            beanMethod = "apply",
            operation = @Operation(operationId = "deleteOrder",
                    tags = "Order use cases",
                    parameters = {
                            @Parameter(
                                    name = "orderId",
                                    description = "Order Id",
                                    required = true,
                                    in = ParameterIn.PATH
                            )
                    },
                    responses = {
                            @ApiResponse(
                                    responseCode = "200",
                                    description = "Order deleted successfully"
                            ),
                            @ApiResponse(responseCode = "404",
                                    description = "Order not found"
                            )
                    }
            )
    )
    public RouterFunction<ServerResponse> deleteOrder(DeleteOrderUseCase deleteOrderUseCase){
        return route(DELETE("/api/orders/{orderId}"),
                request ->  deleteOrderUseCase.apply(request.pathVariable("orderId"))
                        .flatMap(result -> ServerResponse.status(204)
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(result))
                        .onErrorResume(throwable -> ServerResponse.status(HttpStatus.NOT_FOUND).build()));
    }

    @Bean
    @RouterOperation(path = "/api/orders/{orderId}/{state}",
            produces = {MediaType.APPLICATION_JSON_VALUE},
            beanClass = UpdateStateOrderUseCase.class,
            method = RequestMethod.PUT,
            beanMethod = "apply",
            operation = @Operation(
                    operationId = "updateOrder",
                    tags = "Order use cases",
                    parameters = {
                            @Parameter(
                                    name = "orderId",
                                    description = "Order Id",
                                    required = true,
                                    in = ParameterIn.PATH
                            ),
                            @Parameter(
                                    name = "state",
                                    description = "Order state",
                                    required = true,
                                    in = ParameterIn.PATH
                            )
                    },
                    responses = {
                            @ApiResponse(
                                    responseCode = "201",
                                    description = "Success",
                                    content = @Content(schema = @Schema(implementation = Order.class))
                            ),
                            @ApiResponse(responseCode = "406", description = "Not acceptable")}
            )
    )
    public RouterFunction<ServerResponse> updateOrderState(UpdateStateOrderUseCase updateStateOrderUseCase) {
        return route(PUT("/api/orders/{orderId}/{state}"),
                request -> updateStateOrderUseCase.apply(request.pathVariable("orderId"), Integer.valueOf(request.pathVariable("state")))
                        .switchIfEmpty(Mono.error(new Throwable(HttpStatus.NO_CONTENT.toString())))
                        .flatMap(customer -> ServerResponse.ok()
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(customer))
                        .onErrorResume(throwable -> ServerResponse.notFound().build()));
    }

}
