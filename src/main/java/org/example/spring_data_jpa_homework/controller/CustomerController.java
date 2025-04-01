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
    public ResponseEntity<?> addNewCustomer (@RequestBody CustomerRequest customerRequest){
        CustomerResponse customerResponse = customerService.addNewCustomer(customerRequest);
        return new ResponseEntity<>(new APIResponse<>("New customer added successfully",
                HttpStatus.CREATED,customerResponse,201, LocalDateTime.now()
        ),HttpStatus.CREATED);
    }

    @GetMapping("get-all")
    @Operation(summary = "Get all customers")
    public ResponseEntity<?> getAllCustomer(@RequestParam(defaultValue = "1") Integer pageNumber,
                                            @RequestParam(defaultValue = "5") Integer pageSize,
                                            @RequestParam(defaultValue = "id") String sortField,
                                            @RequestParam SortDirection sortDirection
    ){

        List<CustomerResponse> customerResponses = customerService.getAllCustomer(
                pageNumber,
                pageSize,
                sortField,
                sortDirection);
        return new ResponseEntity<>(new APIResponse<>("Get all customers successfully",
                HttpStatus.OK,customerResponses,200, LocalDateTime.now()
        ),HttpStatus.OK);
    }

    @PutMapping("update-customer/{customerId}")
    @Operation(summary = "Update customer by ID")
    public ResponseEntity<?> updateProductById(@PathVariable Long customerId,@RequestBody CustomerRequest customerRequest){
        CustomerResponse customerResponse = customerService.updateCustomerById(customerId,customerRequest);
        return new ResponseEntity<>(new APIResponse<>("Updated customer by ID successfully",
                HttpStatus.OK,customerResponse,200, LocalDateTime.now()
        ),HttpStatus.OK);
    }

    @GetMapping("get-by-id/{customerId}")
    @Operation(summary = "Get customer by ID")
    public ResponseEntity<?> getCustomerById(@PathVariable Long customerId){
        CustomerResponse customerResponse = customerService.getCustomerById(customerId);
        return new ResponseEntity<>(new APIResponse<>("Get customer by ID successfully",
                HttpStatus.OK,customerResponse,200, LocalDateTime.now()
        ),HttpStatus.OK);
    }

    @DeleteMapping("delete-by-id/{customerId}")
    @Operation(summary = "Delete customer by ID")
    public ResponseEntity<?> deleteCustomerById(@PathVariable Long customerId){
        customerService.deleteCustomerById(customerId);
        return new ResponseEntity<>(new APIResponse<>("Deleted customer by ID successfully",
                HttpStatus.OK,null,200, LocalDateTime.now()
        ),HttpStatus.OK);
    }

}