package com.example.orderservice.controller;

import com.example.orderservice.domain.Order;
import com.example.orderservice.dto.OrderCreationDto;
import com.example.orderservice.dto.OrderDto;
import com.example.orderservice.dto.mapping.OrderMapper;
import com.example.orderservice.service.impl.OrderServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class OrderController extends RootController {

    private final OrderServiceImpl orderServiceImpl;
    private final OrderMapper orderMapper;

    @GetMapping(path = "/orders")
    @ResponseStatus(HttpStatus.OK)
    public List<OrderDto> fetchAllOrders() {
        return orderServiceImpl.findAllOrders();
    }

    @GetMapping(path = "/orders/{orderId}")
    @ResponseStatus(HttpStatus.OK)
    public OrderDto findOrderById(@PathVariable String orderId) {
        final Order order = orderServiceImpl.findOrderById(orderId);
        return orderMapper.orderToOrderDto(order);
    }

    @PostMapping(path = "/orders/order.add")
    @ResponseStatus(HttpStatus.CREATED)
    public OrderDto addOrder(@Valid @RequestBody OrderCreationDto orderCreationDto) {
        return orderServiceImpl.addOrder(orderCreationDto);
    }

    @DeleteMapping(path = "/orders/{orderId}/order.delete")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void addOrder(@PathVariable String orderId) {
        orderServiceImpl.deleteOrderById(orderId);
    }
}
