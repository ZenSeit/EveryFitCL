package com.diegob.everyfit.model.clothingitem;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class ClothingItem {

    private String id;
    private String name;
    private String description;
    private double price;
    private String urlImage;
    private Category category;
    private int quantity;

}
