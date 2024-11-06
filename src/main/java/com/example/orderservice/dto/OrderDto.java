package com.example.orderservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;

import java.util.List;

@Data
@AllArgsConstructor
public class OrderDto {

    @Id
    private String id;

    @NotBlank
    private String paymentId;

    @NotBlank
    private String userId;

    @NotNull
    private List<String> booksIds;
}
