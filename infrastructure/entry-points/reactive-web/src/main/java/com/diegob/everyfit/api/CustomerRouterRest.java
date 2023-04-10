package com.diegob.everyfit.api;

import com.diegob.everyfit.model.clothingitem.ClothingItem;
import com.diegob.everyfit.model.customer.Customer;
import com.diegob.everyfit.usecase.getallcustomers.GetAllCustomersUseCase;
import com.diegob.everyfit.usecase.getcustomerbyid.GetCustomerByIdUseCase;
import com.diegob.everyfit.usecase.getitembyid.GetItemByIdUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
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

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class CustomerRouterRest {

    @Bean
    @RouterOperation(path = "/api/customers",
            produces = {MediaType.APPLICATION_JSON_VALUE},
            beanClass = GetAllCustomersUseCase.class,
            method = RequestMethod.GET,
            beanMethod = "get",
            operation = @Operation(operationId = "getAllCustomers",
                    tags = "Customers use cases",
                    responses = {
                            @ApiResponse(responseCode = "200",
                                    description = "Success",
                                    content = @Content(schema = @Schema(implementation = Customer.class))),
                            @ApiResponse(responseCode = "204", description = "No customers found")}
            )
    )
    public RouterFunction<ServerResponse> getAllCustomers(GetAllCustomersUseCase getAllCustomersUseCase){
        return route(GET("/api/customers"),
                request -> ServerResponse.status(200)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromPublisher(getAllCustomersUseCase.get(), Customer.class))
                        .onErrorResume(throwable -> ServerResponse.status(HttpStatus.NO_CONTENT).bodyValue(throwable.getMessage())));
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
    public RouterFunction<ServerResponse> getCustomerById(GetCustomerByIdUseCase getCustomerByIdUseCase){
        return route(GET("/api/customers/{customerId}"),
                request -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromPublisher(getCustomerByIdUseCase.apply(request.pathVariable("customerId")), Customer.class))
                        .onErrorResume(throwable -> ServerResponse.noContent().build()));

    }
}
