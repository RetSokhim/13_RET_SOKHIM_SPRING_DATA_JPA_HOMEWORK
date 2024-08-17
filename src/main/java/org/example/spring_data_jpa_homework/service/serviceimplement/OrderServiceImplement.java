package org.example.spring_data_jpa_homework.service.serviceimplement;

import org.example.spring_data_jpa_homework.model.entity.Order;
import org.example.spring_data_jpa_homework.model.entity.Product;
import org.example.spring_data_jpa_homework.model.entity.ProductOrder;
import org.example.spring_data_jpa_homework.model.enumerations.Status;
import org.example.spring_data_jpa_homework.model.request.OrderRequest;
import org.example.spring_data_jpa_homework.model.response.OrderResponse;
import org.example.spring_data_jpa_homework.model.response.ProductResponse;
import org.example.spring_data_jpa_homework.repository.CustomerRepository;
import org.example.spring_data_jpa_homework.repository.OrderRepository;
import org.example.spring_data_jpa_homework.repository.ProductRepository;
import org.example.spring_data_jpa_homework.service.OrderService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServiceImplement implements OrderService {
    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;

    public OrderServiceImplement(OrderRepository orderRepository, CustomerRepository customerRepository, ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.customerRepository = customerRepository;
        this.productRepository = productRepository;
    }


    @Override
    public OrderResponse addNewOrder(Long customerId, List<OrderRequest> orderRequests) {
        List<ProductResponse> productResponses = new ArrayList<>();
        float totalAmount = 0.00f;
        Order order = new Order();
        order.setOrderDate(LocalDate.now());
        order.setCustomer(customerRepository.findById(customerId).orElseThrow());
        List<ProductOrder> productOrders = new ArrayList<>();
        for (OrderRequest orderRequest : orderRequests) {
            Product product = productRepository.findById(orderRequest.getProductId())
                    .orElseThrow();
            ProductOrder productOrder = new ProductOrder();
            productOrder.setProduct(product);
            productOrder.setOrder(order);
            productOrder.setQuantity(orderRequest.getQuantity());
            productOrders.add(productOrder);
            totalAmount += product.getUnitPrice() * orderRequest.getQuantity();

            productResponses.add(product.toResponse());
        }
        order.setTotalAmount(totalAmount);
        order.setProductOrders(productOrders);
        order.setStatus(Status.PENDING);
        Order savedOrder = orderRepository.save(order);
        return new OrderResponse(savedOrder.getId(),
                savedOrder.getOrderDate(),
                savedOrder.getTotalAmount(),
                savedOrder.getStatus().toString(),
                productResponses);
    }

    @Override
    public List<OrderResponse> getAllOrder(Long customerId) {
        List<Order> orders = orderRepository.findAllByCustomerId(customerId);
        List<OrderResponse> orderResponses = new ArrayList<>();
        for (Order order : orders) {
            List<ProductResponse> productResponses = new ArrayList<>();
            for (ProductOrder productOrder : order.getProductOrders()) {
                ProductResponse productResponse = productOrder.getProduct().toResponse();
                productResponses.add(productResponse);
            }
            OrderResponse orderResponse = new OrderResponse(
                    order.getId(),
                    order.getOrderDate(),
                    order.getTotalAmount(),
                    order.getStatus().toString(),
                    productResponses
            );
            orderResponses.add(orderResponse);
        }
        return orderResponses;
    }

    @Override
    public OrderResponse getOrderById(Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow();
        List<ProductResponse> productResponses = new ArrayList<>();
        for(ProductOrder productOrder : order.getProductOrders()){
            Product product = productRepository.findById(productOrder.getProduct().getId()).orElseThrow();
            productResponses.add(product.toResponse());
        }
        return new OrderResponse(order.getId(),
                order.getOrderDate(),
                order.getTotalAmount(),
                order.getStatus().toString(),
                productResponses);
    }

    @Override
    public OrderResponse updateOrderById(Long orderId, Status status) {
        Order order = orderRepository.findById(orderId).orElseThrow();
        order.setStatus(status);
        List<ProductResponse> productResponses = new ArrayList<>();
        for(ProductOrder productOrder : order.getProductOrders()){
            Product product = productRepository.findById(productOrder.getProduct().getId()).orElseThrow();
            productResponses.add(product.toResponse());
        }
        orderRepository.save(order);
        return new OrderResponse(order.getId(),
                order.getOrderDate(),
                order.getTotalAmount(),
                order.getStatus().toString(),
                productResponses);
    }
}
