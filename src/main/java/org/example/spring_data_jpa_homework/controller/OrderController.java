package org.example.spring_data_jpa_homework.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.example.spring_data_jpa_homework.model.enumerations.SortDirection;
import org.example.spring_data_jpa_homework.model.enumerations.Status;
import org.example.spring_data_jpa_homework.model.request.OrderRequest;
import org.example.spring_data_jpa_homework.model.response.APIResponse;
import org.example.spring_data_jpa_homework.model.response.OrderResponse;
import org.example.spring_data_jpa_homework.model.response.ProductResponse;
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

    @PostMapping("add-order/{customerId}")
    @Operation(summary = "Add new order")
    public ResponseEntity<?> addNewOrder(@PathVariable Long customerId, @RequestBody List<OrderRequest> orderRequest){
        OrderResponse orderResponse = orderService.addNewOrder(customerId,orderRequest);
        return new ResponseEntity<>(new APIResponse<>("New order added successfully",
                HttpStatus.CREATED,orderResponse,201, LocalDateTime.now()
        ),HttpStatus.CREATED);
    }

    @GetMapping("get-all/{customerId}")
    @Operation(summary = "Get all orders by customer ID")
    public ResponseEntity<?> getAllOrder(@PathVariable Long customerId){
        List<OrderResponse> orderResponses = orderService.getAllOrder(customerId);
        return new ResponseEntity<>(new APIResponse<>("Get all orders successfully",
                HttpStatus.OK,orderResponses,200, LocalDateTime.now()
        ),HttpStatus.OK);
    }

    @GetMapping("get-by-id/{orderId}")
    @Operation(summary = "Get order by order ID")
    public ResponseEntity<?> getOrderById(@PathVariable Long orderId){
        OrderResponse orderResponses = orderService.getOrderById(orderId);
        return new ResponseEntity<>(new APIResponse<>("Get order by ID successfully",
                HttpStatus.OK,orderResponses,200, LocalDateTime.now()
        ),HttpStatus.OK);
    }

    @PutMapping("update-by-id/{orderId}")
    @Operation(summary = "update order by order ID")
    public ResponseEntity<?> updateOrderById(@PathVariable Long orderId, @RequestParam Status status){
        OrderResponse orderResponses = orderService.updateOrderById(orderId,status);
        return new ResponseEntity<>(new APIResponse<>("updated order by ID successfully",
                HttpStatus.OK,orderResponses,200, LocalDateTime.now()
        ),HttpStatus.OK);
    }
}
