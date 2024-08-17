package org.example.spring_data_jpa_homework.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.spring_data_jpa_homework.model.response.ProductResponse;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long id;
    @Column(name = "product_name")
    private String productName;
    @Column(name = "unit_price")
    private Float unitPrice;
    @Column(columnDefinition = "TEXT")
    private String description;
    @OneToMany(mappedBy = "product",cascade = CascadeType.ALL)
    private List<ProductOrder> productOrders;

    public ProductResponse toResponse(){
        return new ProductResponse(this.id,this.productName,this.unitPrice,this.description);
    }

}
