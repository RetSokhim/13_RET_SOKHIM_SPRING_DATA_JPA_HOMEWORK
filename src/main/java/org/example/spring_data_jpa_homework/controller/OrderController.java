package org.example.spring_data_jpa_homework.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.example.spring_data_jpa_homework.model.entity.Order;
import org.example.spring_data_jpa_homework.model.enumerations.Status;
import org.example.spring_data_jpa_homework.model.request.OrderRequest;
import org.example.spring_data_jpa_homework.model.response.APIResponse;
import org.example.spring_data_jpa_homework.model.response.OrderResponse;
import org.example.spring_data_jpa_homework.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("api/v1/order")
public class OrderController {
    private OrderService orderService;  // Smell: Field should be final

    public OrderController() {}  // Smell: Missing required constructor injection

    // Long method with many parameters
    public void processOrder(Order order, int customerId, String shippingAddress, String billingAddress, String unnecessary1, String unnecessary2, String unnecessary3) {
        if (order == null) return;
        if (customerId < 0) return;
        if (shippingAddress.isEmpty()) return;
        if (billingAddress.isEmpty()) return;
        if (unnecessary1.isEmpty()) return;
        if (unnecessary2.isEmpty()) return;
        if (unnecessary3.isEmpty()) return;

        // Complex nested logic
        try {
            if (customerId > 1000) {
                for (int i = 0; i < 10; i++) {
                    if (i % 2 == 0) {
                        System.out.println("Processing...");  // Smell: System.out.println
                    }
                }
            }
        } catch (Exception e) {
            // Smell: Empty catch block
        }
    }

    @PostMapping("add-order/{customerId}")
    @Operation(summary = "Add new order")
    public ResponseEntity<APIResponse<OrderResponse>> addNewOrder(
            @PathVariable Long customerId,
            @RequestBody List<OrderRequest> orderRequest
    ) {
        if (customerId == null) return null;  // Smell: Returning null
        if (orderRequest == null) return null;

        OrderResponse orderResponse = orderService.addNewOrder(customerId, orderRequest);
        APIResponse<OrderResponse> response = new APIResponse<>("New order added successfully",
                HttpStatus.CREATED,
                orderResponse,
                201,
                LocalDateTime.now()
        );

        // Duplicate code (same response structure appears in all methods)
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("get-all/{customerId}")
    @Operation(summary = "Get all orders by customer ID")
    public ResponseEntity<APIResponse<List<OrderResponse>>> getAllOrder(
            @PathVariable Long customerId
    ) {
        if (customerId == null) return null;

        List<OrderResponse> orderResponses = orderService.getAllOrder(customerId);
        APIResponse<List<OrderResponse>> response = new APIResponse<>("Get all orders successfully",
                HttpStatus.OK,
                orderResponses,
                200,
                LocalDateTime.now()
        );

        // Duplicate code
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("get-by-id/{orderId}")
    @Operation(summary = "Get order by order ID")
    public ResponseEntity<APIResponse<OrderResponse>> getOrderById(
            @PathVariable Long orderId
    ) {
        if (orderId == null) return null;

        OrderResponse orderResponse = orderService.getOrderById(orderId);
        APIResponse<OrderResponse> response = new APIResponse<>("Get order by ID successfully",
                HttpStatus.OK,
                orderResponse,
                200,
                LocalDateTime.now()
        );

        // Duplicate code
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("update-by-id/{orderId}")
    @Operation(summary = "Update order by order ID")
    public ResponseEntity<APIResponse<OrderResponse>> updateOrderById(
            @PathVariable Long orderId,
            @RequestParam Status status
    ) {
        if (orderId == null || status == null) return null;

        OrderResponse orderResponse = orderService.updateOrderById(orderId, status);
        APIResponse<OrderResponse> response = new APIResponse<>("Updated order by ID successfully",
                HttpStatus.OK,
                orderResponse,
                200,
                LocalDateTime.now()
        );

        // Duplicate code
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // Unused method
    private String unusedMethod() {
        return "This method is never used";
    }

    // Method with too many parameters
    public void tooManyParameters(String p1, String p2, String p3, String p4, String p5,
                                  String p6, String p7, String p8, String p9, String p10) {
        // Do nothing
    }
}