package com.diegob.everyfit.api;

import com.diegob.everyfit.model.order.Order;
import com.diegob.everyfit.usecase.generateorder.GenerateOrderUseCase;
import com.diegob.everyfit.usecase.getordersbycustomer.GetOrdersByCustomerUseCase;
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
                request -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromPublisher(getOrdersByCustomerUseCase.apply(request.pathVariable("customerId")), Order.class))
                        .onErrorResume(throwable -> ServerResponse.noContent().build()));

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

}
