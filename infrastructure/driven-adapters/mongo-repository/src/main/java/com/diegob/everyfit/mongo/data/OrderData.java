package com.diegob.everyfit.mongo.data;

import com.diegob.everyfit.model.order.OrderState;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.UUID;

@Data
@Document(collection = "orders")
@NoArgsConstructor
@AllArgsConstructor
public class OrderData {

    @Id
    private String id= UUID.randomUUID().toString().substring(0,10);
    private String customer;
    private List<ItemData> products;
    private OrderState state = OrderState.PAYMENT;

}
