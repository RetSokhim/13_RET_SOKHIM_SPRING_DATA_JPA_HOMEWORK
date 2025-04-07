package org.example.spring_data_jpa_homework.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.example.spring_data_jpa_homework.model.enumerations.SortDirection;
import org.example.spring_data_jpa_homework.model.request.CustomerRequest;
import org.example.spring_data_jpa_homework.model.response.APIResponse;
import org.example.spring_data_jpa_homework.model.response.CustomerResponse;
import org.example.spring_data_jpa_homework.service.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("api/v1/customer")
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping("create-customer")
    @Operation(summary = "Add new customer")
    public ResponseEntity<APIResponse<CustomerResponse>> addNewCustomer(@RequestBody CustomerRequest customerRequest){
        CustomerResponse customerResponse = customerService.addNewCustomer(customerRequest);
        APIResponse<CustomerResponse> response = new APIResponse<>(
                "New customer added successfully",
                HttpStatus.CREATED,
                customerResponse,
                201,
                LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("get-all")
    @Operation(summary = "Get all customers")
    public ResponseEntity<APIResponse<List<CustomerResponse>>> getAllCustomer(
            @RequestParam(defaultValue = "1") Integer pageNumber,
            @RequestParam(defaultValue = "5") Integer pageSize,
            @RequestParam(defaultValue = "id") String sortField,
            @RequestParam SortDirection sortDirection){

        List<CustomerResponse> customerResponses = customerService.getAllCustomer(
                pageNumber, pageSize, sortField, sortDirection);
        APIResponse<List<CustomerResponse>> response = new APIResponse<>(
                "Get all customers successfully",
                HttpStatus.OK,
                customerResponses,
                200,
                LocalDateTime.now()
        );
        return ResponseEntity.ok(response);
    }

    @PutMapping("update-customer/{customerId}")
    @Operation(summary = "Update customer by ID")
    public ResponseEntity<APIResponse<CustomerResponse>> updateProductById(
            @PathVariable Long customerId, @RequestBody CustomerRequest customerRequest){
        CustomerResponse customerResponse = customerService.updateCustomerById(customerId, customerRequest);
        APIResponse<CustomerResponse> response = new APIResponse<>(
                "Updated customer by ID successfully",
                HttpStatus.OK,
                customerResponse,
                200,
                LocalDateTime.now()
        );
        return ResponseEntity.ok(response);
    }

    @GetMapping("get-by-id/{customerId}")
    @Operation(summary = "Get customer by ID")
    public ResponseEntity<APIResponse<CustomerResponse>> getCustomerById(@PathVariable Long customerId){
        CustomerResponse customerResponse = customerService.getCustomerById(customerId);
        APIResponse<CustomerResponse> response = new APIResponse<>(
                "Get customer by ID successfully",
                HttpStatus.OK,
                customerResponse,
                200,
                LocalDateTime.now()
        );
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("delete-by-id/{customerId}")
    @Operation(summary = "Delete customer by ID")
    public ResponseEntity<APIResponse<Void>> deleteCustomerById(@PathVariable Long customerId){
        customerService.deleteCustomerById(customerId);
        APIResponse<Void> response = new APIResponse<>(
                "Deleted customer by ID successfully",
                HttpStatus.OK,
                null,
                200,
                LocalDateTime.now()
        );
        return ResponseEntity.ok(response);
    }
}
