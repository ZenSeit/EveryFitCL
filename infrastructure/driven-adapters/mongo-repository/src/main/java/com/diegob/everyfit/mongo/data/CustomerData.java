package com.diegob.everyfit.mongo.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Data
@Document(collection = "customers")
@NoArgsConstructor
@AllArgsConstructor
public class CustomerData {

    @Id
    private String id= UUID.randomUUID().toString().substring(0,10);
    private String name;
    private String lastName;
    private String email;
    private String Address;

}
