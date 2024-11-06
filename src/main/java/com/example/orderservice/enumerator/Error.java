package com.example.orderservice.enumerator;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Enum representing different error types and their messages.
 */
@Getter
@AllArgsConstructor
public enum Error {

    PAYMENT_NOT_FOUND("PAYMENT_NOT_FOUND", "Pagamento não encontrado"),
    ORDER_NOT_FOUND("ORDER_NOT_FOUND", "Pedido não encontrado"),
    CART_NOT_FOUND("CART_NOT_FOUND", "Carrinho não encontrado"),
    BOOK_NOT_FOUND("BOOK_NOT_FOUND", "Livro não encontrado"),
    USER_NOT_FOUND("USER_NOT_FOUND", "Usuário não encontrado");

    private final String errorMessage;
    private final String errorDescription;
}
