package com.diegob.everyfit.usecase;

import com.diegob.everyfit.model.clothingitem.Category;
import com.diegob.everyfit.model.clothingitem.ClothingItem;
import com.diegob.everyfit.model.customer.Customer;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class DataMocks {

    public static Flux<Customer> allCustomer(){
        return Flux.just(new Customer("1", "Diego", "Becerra","diego@correo.com","123456789"),
                new Customer("2", "Brad", "Bird","brad@correo.com","street 1"));
    };

    public static Flux<Customer> allCustomerEmpty(){
        return Flux.empty();
    };

    public static Flux<ClothingItem> allClothingItem(){
        return Flux.just(new ClothingItem("1", "Polo", "M", 15.60, "Cotton", Category.MEN, 5),
                new ClothingItem("2", "Polo", "M", 19.99, "Cotton", Category.MEN, 3));
    };

    public static Flux<ClothingItem> allClothingItemEmpty(){
        return Flux.empty();
    };

    public static Mono<Customer> customerById(){
        return Mono.just(new Customer("1", "Diego", "Becerra","diego@correo.com","123456789"));
    };

    public static Mono<Customer> customerByIdEmpty(){
        return Mono.empty();
    };


}
