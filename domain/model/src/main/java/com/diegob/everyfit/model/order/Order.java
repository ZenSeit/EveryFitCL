package com.diegob.everyfit.model.order;
import com.diegob.everyfit.model.clothingitem.ClothingItem;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Order {

    private String id;
    private String customer;
    private List<ClothingItem> products;
    private OrderState state = OrderState.PAYMENT;

}
