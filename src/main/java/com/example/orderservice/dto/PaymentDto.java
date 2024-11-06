package com.example.orderservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PaymentDto {

    @NotBlank
    private String id;

    @NotBlank
    private String cartId;

    @NotNull
    private Float amount;
}
