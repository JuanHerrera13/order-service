package com.example.orderservice.integration;

import com.example.orderservice.dto.BookDto;
import com.example.orderservice.exception.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import static com.example.orderservice.enumerator.Error.BOOK_NOT_FOUND;

@Slf4j
@Service
public class BookApiClient {

    private static final String BOOK_SERVICE_URL = "http://localhost:8080/book-service/v1/books";

    public BookDto getBookById(String bookId) {
        RestTemplate restTemplate = new RestTemplate();
        try {
            log.info("Chamando book service com bookId: {}", bookId);
            ResponseEntity<BookDto> response = restTemplate.getForEntity(BOOK_SERVICE_URL.concat("/id/") +
                    bookId, BookDto.class);
            return response.getBody();
        } catch (HttpClientErrorException.NotFound e) {
            log.error("Livro não encontrado com bookId: {}", bookId);
            throw new NotFoundException(BOOK_NOT_FOUND.getErrorDescription());
        } catch (Exception e) {
            throw new RestClientException(e.getMessage());
        }
    }

    public void purchaseBook(String bookId) {
        RestTemplate restTemplate = new RestTemplate();
        try {
            log.info("Chamando book service com bookId: {}", bookId);
            restTemplate.postForEntity(BOOK_SERVICE_URL.concat("/book.purchase"), bookId, Void.class);
        } catch (HttpClientErrorException.NotFound e) {
            log.error("Livro não encontrado com bookId: {}", bookId);
            throw new NotFoundException(BOOK_NOT_FOUND.getErrorDescription());
        } catch (Exception e) {
            throw new RestClientException(e.getMessage());
        }
    }
}
