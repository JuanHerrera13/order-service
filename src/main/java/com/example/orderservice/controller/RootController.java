package com.example.orderservice.controller;

import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Abstract class that centralizes the path prefix "/order-service/v1"
 * for controller classes that extend RootController.
 */
@RequestMapping(path = "/order-service/v1")
public class RootController {
}
