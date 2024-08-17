package org.example.spring_data_jpa_homework.service;

import org.example.spring_data_jpa_homework.model.enumerations.SortDirection;
import org.example.spring_data_jpa_homework.model.request.ProductRequest;
import org.example.spring_data_jpa_homework.model.response.ProductResponse;

import java.util.List;

public interface ProductService {
    ProductResponse addNewProduct(ProductRequest productRequest);
    List<ProductResponse> getAllProduct(Integer pageNumber, Integer pageSize, String sortField, SortDirection sortDirection);

    ProductResponse updateProductById(Long productId, ProductRequest productRequest);

    ProductResponse getProductById(Long productId);

    void deleteProductById(Long productId);
}
