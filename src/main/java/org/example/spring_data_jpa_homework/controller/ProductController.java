package org.example.spring_data_jpa_homework.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.example.spring_data_jpa_homework.model.enumerations.SortDirection;
import org.example.spring_data_jpa_homework.model.request.ProductRequest;
import org.example.spring_data_jpa_homework.model.response.APIResponse;
import org.example.spring_data_jpa_homework.model.response.ProductResponse;
import org.example.spring_data_jpa_homework.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("api/v1/product")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("add-product")
    @Operation(summary = "Add new products")
    public ResponseEntity<?> addNewProduct (@RequestBody ProductRequest productRequest){
        ProductResponse productResponse = productService.addNewProduct(productRequest);
        return new ResponseEntity<>(new APIResponse<>("New product added successfully",
                HttpStatus.CREATED,productResponse,201, LocalDateTime.now()
        ),HttpStatus.CREATED);
    }

    @GetMapping("get-all")
    @Operation(summary = "Get all products")
    public ResponseEntity<?> getAllProduct(@RequestParam(defaultValue = "1") Integer pageNumber,
                                            @RequestParam(defaultValue = "5") Integer pageSize,
                                            @RequestParam(defaultValue = "id") String sortField,
                                            @RequestParam SortDirection sortDirection
    ){
        List<ProductResponse> productResponses = productService.getAllProduct(
                pageNumber,
                pageSize,
                sortField,
                sortDirection);
        return new ResponseEntity<>(new APIResponse<>("Get all products successfully",
                HttpStatus.OK,productResponses,200, LocalDateTime.now()
        ),HttpStatus.OK);
    }

    @PutMapping("update-product/{productId}")
    @Operation(summary = "Update product by ID")
    public ResponseEntity<?> updateProductById(@PathVariable Long productId,@RequestBody ProductRequest productRequest){
        ProductResponse productResponse = productService.updateProductById(productId,productRequest);
        return new ResponseEntity<>(new APIResponse<>("Updated product by ID successfully",
                HttpStatus.OK,productResponse,200, LocalDateTime.now()
        ),HttpStatus.OK);
    }

    @GetMapping("get-by-id/{productId}")
    @Operation(summary = "Get product by ID")
    public ResponseEntity<?> getProductById(@PathVariable Long productId){
        ProductResponse productResponse = productService.getProductById(productId);
        return new ResponseEntity<>(new APIResponse<>("Get product by ID successfully",
                HttpStatus.OK,productResponse,200, LocalDateTime.now()
        ),HttpStatus.OK);
    }

    @DeleteMapping("delete-by-id/{productId}")
    @Operation(summary = "Delete product by ID")
    public ResponseEntity<?> deleteProductById(@PathVariable Long productId){
        productService.deleteProductById(productId);
        return new ResponseEntity<>(new APIResponse<>("Deleted product by ID successfully",
                HttpStatus.OK,null,200, LocalDateTime.now()
        ),HttpStatus.OK);
    }
}
