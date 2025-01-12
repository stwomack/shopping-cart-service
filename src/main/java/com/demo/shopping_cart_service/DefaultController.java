package com.demo.shopping_cart_service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DefaultController {

    @Autowired
    ShoppingCartService shoppingCartService;

    @GetMapping("/")
    public String hello() {
        return "This Spring Boot app is generating ShoppingCarts and storing them in Redis";
    }

    @GetMapping("/shoppingcarts")
    public Iterable<ShoppingCart> getShoppingCarts() {
        return shoppingCartService.getAllCarts();
    }
}
