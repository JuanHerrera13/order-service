package com.example.orderservice.dto.mapping;

import com.example.orderservice.domain.Order;
import com.example.orderservice.dto.OrderCreationDto;
import com.example.orderservice.dto.OrderDto;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface OrderMapper {

    Order orderCreationToOrder(OrderCreationDto orderCreationDto);

    OrderDto orderToOrderDto(Order order);

    List<OrderDto> orderListToOrderListDto(List<Order> orders);
}
