package com.example.orderservice.integration;

import com.example.orderservice.dto.UserDto;
import com.example.orderservice.exception.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import static com.example.orderservice.enumerator.Error.USER_NOT_FOUND;

@Slf4j
@Service
public class UserApiClient {

    private static final String USER_SERVICE_URL = "http://localhost:8082/user-service/v1/users/";

    public UserDto getUserById(String userId) {
        RestTemplate restTemplate = new RestTemplate();
        try {
            log.info("Chamando user service com userId: {}", userId);
            ResponseEntity<UserDto> response = restTemplate.getForEntity(USER_SERVICE_URL + userId, UserDto.class);
            return response.getBody();
        } catch (HttpClientErrorException.NotFound e) {
            log.error("Usuário não encontrado!");
            throw new NotFoundException(USER_NOT_FOUND.getErrorDescription());
        } catch (Exception e) {
            log.error(e.toString());
            throw new RestClientException(e.getMessage());
        }
    }
}
