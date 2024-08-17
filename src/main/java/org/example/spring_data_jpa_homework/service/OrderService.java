package org.example.spring_data_jpa_homework.service;

import org.example.spring_data_jpa_homework.model.enumerations.Status;
import org.example.spring_data_jpa_homework.model.request.OrderRequest;
import org.example.spring_data_jpa_homework.model.response.OrderResponse;

import java.util.List;

public interface OrderService {
    OrderResponse addNewOrder(Long customerId, List<OrderRequest> orderRequest);
    List<OrderResponse> getAllOrder(Long customerId);
    OrderResponse getOrderById(Long orderId);
    OrderResponse updateOrderById(Long orderId, Status status);
}
