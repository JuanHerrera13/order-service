package com.example.orderservice.service;

import com.example.orderservice.domain.Order;
import com.example.orderservice.dto.OrderCreationDto;
import com.example.orderservice.dto.OrderDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OrderService {

    List<OrderDto> findAllOrders();

    Order findOrderById(String id);

    OrderDto addOrder(OrderCreationDto orderCreationDto);

    void deleteOrderById(String orderId);
}
