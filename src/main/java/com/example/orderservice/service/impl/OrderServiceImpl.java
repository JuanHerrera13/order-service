package com.example.orderservice.service.impl;

import com.example.orderservice.domain.Order;
import com.example.orderservice.dto.*;
import com.example.orderservice.dto.mapping.OrderMapper;
import com.example.orderservice.exception.NotFoundException;
import com.example.orderservice.integration.*;
import com.example.orderservice.repository.OrderRepository;
import com.example.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.example.orderservice.enumerator.Error.ORDER_NOT_FOUND;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final PaymentApiClient paymentApiClient;
    private final CartApiClient cartApiClient;
    private final NotificationApiClient notificationApiClient;
    private final BookApiClient bookApiClient;
    private final UserApiClient userApiClient;

    @Override
    public List<OrderDto> findAllOrders() {
        log.info("Fetching all orders");
        final List<Order> orders = orderRepository.findAll();
        return orderMapper.orderListToOrderListDto(orders);
    }

    @Override
    public Order findOrderById(String id) {
        log.info("Fetching order by id");
        return orderRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ORDER_NOT_FOUND.getErrorDescription()));
    }

    @Override
    public OrderDto addOrder(OrderCreationDto orderCreationDto) {
        log.info("Adding new order");
        try {
            final String paymentId = paymentApiClient.getPaymentByCartId(orderCreationDto.getCartId()).getId();
            final CartDto cartDto = cartApiClient.getCartById(orderCreationDto.getCartId());
            final UserDto userDto = userApiClient.getUserById(cartDto.getUserId());
            final List<String> booksNames = new ArrayList<>();

            for (String bookId : cartDto.getBooksIds()) {
                final String bookName = bookApiClient.getBookById(bookId).getTitle();
                booksNames.add(bookName);
            }

            final Order order = Order.builder()
                    .paymentId(paymentId)
                    .booksIds(cartDto.getBooksIds())
                    .userId(cartDto.getUserId())
                    .build();
            orderRepository.save(order);
            cartApiClient.deleteCartById(cartDto.getId());
            notificationApiClient.sendEmail(userDto.getEmail(), userDto.getFirstName(), booksNames);
            log.info("Pedido adicionado com sucesso!");
            return orderMapper.orderToOrderDto(order);
        } catch (NotFoundException e) {
            throw new NotFoundException("Pedido não pôde ser concluído, pois: " + e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public void deleteOrderById(String orderId) {
        this.findOrderById(orderId);
        log.info("Deleting order by id");
        orderRepository.deleteById(orderId);
        log.info("Order deleted");
    }
}
