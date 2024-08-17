package org.example.spring_data_jpa_homework.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponse {
    private Long id;
    private LocalDate orderDate;
    private Float totalAmount;
    private String status;
    private List<ProductResponse> products;
}
