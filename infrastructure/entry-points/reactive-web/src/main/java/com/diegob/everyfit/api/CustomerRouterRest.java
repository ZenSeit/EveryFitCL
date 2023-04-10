package com.diegob.everyfit.api;

import com.diegob.everyfit.model.clothingitem.ClothingItem;
import com.diegob.everyfit.model.customer.Customer;
import com.diegob.everyfit.usecase.createuser.CreateUserUseCase;
import com.diegob.everyfit.usecase.deletecustomer.DeleteCustomerUseCase;
import com.diegob.everyfit.usecase.getallcustomers.GetAllCustomersUseCase;
import com.diegob.everyfit.usecase.getcustomerbyemail.GetCustomerByEmailUseCase;
import com.diegob.everyfit.usecase.getcustomerbyid.GetCustomerByIdUseCase;
import com.diegob.everyfit.usecase.getitembyid.GetItemByIdUseCase;
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
    @RouterOperation(path = "/api/customers/{customerId}",
            produces = {MediaType.APPLICATION_JSON_VALUE},
            beanClass = GetCustomerByIdUseCase.class,
            method = RequestMethod.GET,
            beanMethod = "apply",
            operation = @Operation(operationId = "getCustomerById",
                    tags = "Customers use cases",
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
                                    description = "Customer found",
                                    content = @Content(
                                            schema = @Schema(
                                                    implementation = Customer.class
                                            )
                                    )
                            ),
                            @ApiResponse(responseCode = "404",
                                    description = "Customer not found"
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

    @Bean
    @RouterOperation(path = "/api/customers",
            produces = {MediaType.APPLICATION_JSON_VALUE},
            beanClass = CreateUserUseCase.class,
            method = RequestMethod.POST,
            beanMethod = "apply",
            operation = @Operation(
                    operationId = "createUser",
                    tags = "Customers use cases",
                    parameters = {
                            @Parameter(
                                    name = "Customer",
                                    in = ParameterIn.PATH,
                                    schema = @Schema(implementation = Customer.class)
                            )
                    },
                    responses = {
                            @ApiResponse(
                                    responseCode = "201",
                                    description = "Success",
                                    content = @Content(schema = @Schema(implementation = Customer.class))
                            ),
                            @ApiResponse(responseCode = "406", description = "Not acceptable")},
                    requestBody = @RequestBody(required = true,
                            description = "Save a customer",
                            content = @Content(schema = @Schema(implementation = Customer.class)))
            )
    )
    public RouterFunction<ServerResponse> createCustomer(CreateUserUseCase createUserUseCase){
        return route(POST("/api/customers").and(accept(MediaType.APPLICATION_JSON)),
                request -> request.bodyToMono(Customer.class)
                        .flatMap(customer -> createUserUseCase.apply(customer)
                                .flatMap(result -> ServerResponse.status(201)
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .bodyValue(result))
                                .onErrorResume(throwable -> ServerResponse.status(HttpStatus.NOT_ACCEPTABLE).bodyValue(throwable.getMessage()))));
    }

    @Bean
    @RouterOperation(path = "/api/customers/{email}",
            produces = {MediaType.APPLICATION_JSON_VALUE},
            beanClass = GetCustomerByIdUseCase.class,
            method = RequestMethod.GET,
            beanMethod = "apply",
            operation = @Operation(operationId = "getCustomerByEmail",
                    tags = "Customers use cases",
                    parameters = {
                            @Parameter(
                                    name = "email",
                                    description = "customer id",
                                    required = true,
                                    in = ParameterIn.PATH
                            )
                    },
                    responses = {
                            @ApiResponse(
                                    responseCode = "200",
                                    description = "Customer found",
                                    content = @Content(
                                            schema = @Schema(
                                                    implementation = Customer.class
                                            )
                                    )
                            ),
                            @ApiResponse(responseCode = "404",
                                    description = "Customer not found"
                            )
                    }
            )
    )
    public RouterFunction<ServerResponse> getCustomerByEmail(GetCustomerByEmailUseCase getCustomerByEmailUseCase){
        return route(GET("/api/customers/{email}"),
                request -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromPublisher(getCustomerByEmailUseCase.apply(request.pathVariable("email")), Customer.class))
                        .onErrorResume(throwable -> ServerResponse.noContent().build()));

    }

    @Bean
    @RouterOperation(path = "/api/customers/{customerId}",
            produces = {MediaType.APPLICATION_JSON_VALUE},
            beanClass = DeleteCustomerUseCase.class,
            method = RequestMethod.DELETE,
            beanMethod = "apply",
            operation = @Operation(operationId = "deleteCustomer",
                    tags = "Customers use cases",
                    parameters = {
                            @Parameter(
                                    name = "customerId",
                                    description = "Customer Id",
                                    required = true,
                                    in = ParameterIn.PATH
                            )
                    },
                    responses = {
                            @ApiResponse(
                                    responseCode = "200",
                                    description = "Customer deleted successfully",
                                    content = @Content(
                                            schema = @Schema(
                                                    implementation = Customer.class
                                            )
                                    )
                            ),
                            @ApiResponse(responseCode = "404",
                                    description = "Customer not found"
                            )
                    }
            )
    )
    public RouterFunction<ServerResponse> deleteCustomer(DeleteCustomerUseCase deleteCustomerUseCase){
        return route(DELETE("/api/customers/{customerId}"),
                request ->  deleteCustomerUseCase.apply(request.pathVariable("customerId"))
                        .flatMap(result -> ServerResponse.status(204)
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(result))
                        .onErrorResume(throwable -> ServerResponse.status(HttpStatus.NOT_FOUND).build()));
    }
}
