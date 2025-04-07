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
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }
    // Long method
    public void processOrder(Order order, int customerId, String shippingAddress, String billingAddress) {
        // too many parameters, hard to maintain
    }

    @PostMapping("add-order/{customerId}")
    @Operation(summary = "Add new order")
    public ResponseEntity<APIResponse<OrderResponse>> addNewOrder(
            @PathVariable Long customerId,
            @RequestBody List<OrderRequest> orderRequest
    ) {
        OrderResponse orderResponse = orderService.addNewOrder(customerId, orderRequest);
        return new ResponseEntity<>(
                new APIResponse<>("New order added successfully",
                        HttpStatus.CREATED,
                        orderResponse,
                        201,
                        LocalDateTime.now()
                ),
                HttpStatus.CREATED
        );
    }

    @GetMapping("get-all/{customerId}")
    @Operation(summary = "Get all orders by customer ID")
    public ResponseEntity<APIResponse<List<OrderResponse>>> getAllOrder(
            @PathVariable Long customerId
    ) {
        List<OrderResponse> orderResponses = orderService.getAllOrder(customerId);
        return new ResponseEntity<>(
                new APIResponse<>("Get all orders successfully",
                        HttpStatus.OK,
                        orderResponses,
                        200,
                        LocalDateTime.now()
                ),
                HttpStatus.OK
        );
    }

    @GetMapping("get-by-id/{orderId}")
    @Operation(summary = "Get order by order ID")
    public ResponseEntity<APIResponse<OrderResponse>> getOrderById(
            @PathVariable Long orderId
    ) {
        OrderResponse orderResponse = orderService.getOrderById(orderId);
        return new ResponseEntity<>(
                new APIResponse<>("Get order by ID successfully",
                        HttpStatus.OK,
                        orderResponse,
                        200,
                        LocalDateTime.now()
                ),
                HttpStatus.OK
        );
    }

    @PutMapping("update-by-id/{orderId}")
    @Operation(summary = "Update order by order ID")
    public ResponseEntity<APIResponse<OrderResponse>> updateOrderById(
            @PathVariable Long orderId,
            @RequestParam Status status
    ) {
        OrderResponse orderResponse = orderService.updateOrderById(orderId, status);
        return new ResponseEntity<>(
                new APIResponse<>("Updated order by ID successfully",
                        HttpStatus.OK,
                        orderResponse,
                        200,
                        LocalDateTime.now()
                ),
                HttpStatus.OK
        );
    }
}
