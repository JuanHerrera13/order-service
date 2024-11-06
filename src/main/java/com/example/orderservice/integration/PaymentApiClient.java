package com.example.orderservice.integration;

import com.example.orderservice.dto.PaymentDto;
import com.example.orderservice.exception.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import static com.example.orderservice.enumerator.Error.PAYMENT_NOT_FOUND;

@Slf4j
@Service
public class PaymentApiClient {

    private static final String PAYMENT_SERVICE_URL = "http://localhost:8081/payment-service/v1/";

    public PaymentDto getPaymentByCartId(String cartId) {
        RestTemplate restTemplate = new RestTemplate();
        try {
            log.info("Chamando payment service com cartId: {}", cartId);
            ResponseEntity<PaymentDto> response = restTemplate
                    .getForEntity(PAYMENT_SERVICE_URL.concat("payments/cart/") + cartId, PaymentDto.class);
            return response.getBody();
        } catch (HttpClientErrorException.NotFound e) {
            log.error("Pagamento não encontrado!");
            throw new NotFoundException(PAYMENT_NOT_FOUND.getErrorDescription());
        } catch (Exception e) {
            log.error(e.toString());
            throw new RestClientException(e.getMessage());
        }
    }
}
