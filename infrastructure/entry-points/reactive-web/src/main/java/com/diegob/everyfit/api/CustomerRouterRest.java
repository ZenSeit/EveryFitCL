package com.diegob.everyfit.api;

import com.diegob.everyfit.model.customer.Customer;
import com.diegob.everyfit.usecase.getallcustomers.GetAllCustomersUseCase;
import io.swagger.v3.oas.annotations.Operation;
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
}
