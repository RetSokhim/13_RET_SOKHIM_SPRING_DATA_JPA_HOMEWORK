package org.example.spring_data_jpa_homework.service.serviceimplement;

import org.example.spring_data_jpa_homework.model.entity.Product;
import org.example.spring_data_jpa_homework.model.enumerations.SortDirection;
import org.example.spring_data_jpa_homework.model.request.ProductRequest;
import org.example.spring_data_jpa_homework.model.response.ProductResponse;
import org.example.spring_data_jpa_homework.repository.ProductRepository;
import org.example.spring_data_jpa_homework.service.ProductService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImplement implements ProductService {
    private final ProductRepository productRepository;

    public ProductServiceImplement(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public ProductResponse addNewProduct(ProductRequest productRequest) {
        Product product = productRequest.toProduct();
        return productRepository.save(product).toResponse();
    }

    @Override
    public List<ProductResponse> getAllProduct(Integer pageNumber, Integer pageSize, String sortField, SortDirection sortDirection) {
        Sort sort = sortDirection.name().equalsIgnoreCase(Sort.Direction.ASC.name())?
                Sort.by(sortField).ascending() : Sort.by(sortField).descending();
        Pageable pageable = PageRequest.of(pageNumber-1,pageSize,sort);
        return toResponse(productRepository.findAll(pageable).getContent());
    }

    @Override
    public ProductResponse updateProductById(Long productId, ProductRequest productRequest) {
        Product product = productRepository.findById(productId).orElseThrow();
        product.setProductName(productRequest.getProductName());
        product.setUnitPrice(productRequest.getUnitPrice());
        product.setDescription(productRequest.getDescription());
        return productRepository.save(product).toResponse();
    }

    @Override
    public ProductResponse getProductById(Long productId) {
        return productRepository.findById(productId).orElseThrow().toResponse();
    }

    @Override
    public void deleteProductById(Long productId) {
        productRepository.deleteById(productId);
    }

    public List<ProductResponse> toResponse (List<Product> products){
        return products.stream().map(Product::toResponse).collect(Collectors.toList());
    }
}
