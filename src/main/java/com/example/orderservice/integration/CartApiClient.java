package com.example.orderservice.integration;

import com.example.orderservice.dto.CartDto;
import com.example.orderservice.exception.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import static com.example.orderservice.enumerator.Error.CART_NOT_FOUND;

@Slf4j
@Service
public class CartApiClient {

    private static final String CART_SERVICE_URL = "http://localhost:8084/cart-service/v1/";

    public CartDto getCartById(String cartId) {
        RestTemplate restTemplate = new RestTemplate();
        try {
            log.info("Chamando cart service com cartId: {}", cartId);
            ResponseEntity<CartDto> response = restTemplate.getForEntity(CART_SERVICE_URL.concat("carts/") + cartId, CartDto.class);
            return response.getBody();
        } catch (HttpClientErrorException.NotFound e) {
            log.error("Carrinho não encontrado!");
            throw new NotFoundException(CART_NOT_FOUND.getErrorDescription());
        } catch (Exception e) {
            log.error(e.toString());
            throw new RestClientException(e.getMessage());
        }
    }

    public void deleteCartById(String cartId) {
        RestTemplate restTemplate = new RestTemplate();
        try {
            log.info("Deletando carrinho com cartId: {}", cartId);
            restTemplate.delete(CART_SERVICE_URL.concat("carts/") + cartId.concat("/cart.delete"));
            log.info("Carrinho deletado com sucesso!");
        } catch (HttpClientErrorException.NotFound e) {
            log.error("Carrinho não encontrado para deletar!");
            throw new NotFoundException(CART_NOT_FOUND.getErrorDescription());
        } catch (Exception e) {
            log.error("Erro ao deletar carrinho: " + e.getMessage());
            throw new RestClientException(e.getMessage());
        }
    }
}
