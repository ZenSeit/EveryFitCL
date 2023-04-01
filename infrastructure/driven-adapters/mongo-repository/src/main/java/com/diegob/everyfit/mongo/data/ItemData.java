package com.diegob.everyfit.mongo.data;

import com.diegob.everyfit.model.clothingitem.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Data
@Document(collection = "items")
@NoArgsConstructor
@AllArgsConstructor
public class ItemData {

    @Id
    private String id=UUID.randomUUID().toString().substring(0,10);
    private String name;
    private String description;
    private double price;
    private String urlImage;
    private Category category;
    private int quantity;

}
