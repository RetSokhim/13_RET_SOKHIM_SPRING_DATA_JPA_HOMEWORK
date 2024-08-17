package org.example.spring_data_jpa_homework.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.spring_data_jpa_homework.model.entity.Product;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequest {
    private String productName;
    private Float unitPrice;
    private String description;

    public Product toProduct(){
        return new Product(null,this.productName,this.unitPrice,this.description,null);
    }
}
