package com.demo.shopping_cart_service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@EnableScheduling
public class ShoppingCartService {
    private final Logger log = LoggerFactory.getLogger(ShoppingCartService.class);

    @Autowired
    private ShoppingCartRepository cartRepository;

    @Scheduled(fixedRate = 5000) // Generate new carts every 5 seconds
    public void generateRandomCart() {
        String userId = UUID.randomUUID().toString();
        ShoppingCart cart = new ShoppingCart(userId, generateRandomItems());
        log.info("Adding Cart: {}", cart.toString());
        cartRepository.save(cart);
        log.info("Getting Cart: {}", getCart(userId));
    }

    public ShoppingCart getCart(String userId) {
        return cartRepository.findById(userId).orElse(null);
    }

    public Iterable<ShoppingCart> getAllCarts() {
        return cartRepository.findAll();
    }

    private List<Item> generateRandomItems() {
        List<Item> items = new ArrayList<>();
        int numItems = (int) (Math.random() * 5) + 1; // Between 1 and 5 items
        for (int i = 0; i < numItems; i++) {
            items.add(new Item(
                    generateRandomSkuCode(),
                    generateRandomPrice(),
                    (int) (Math.random() * 5) + 1 // Random quantity
            ));
        }
        return items;
    }

    private String generateRandomUserId() {
        String uuid = UUID.randomUUID().toString();
        return uuid.replace("-", "");
    }

    private String generateRandomSkuCode() {
        // Example: Generate a 6-character random alphanumeric code
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            int index = (int) (Math.random() * chars.length());
            sb.append(chars.charAt(index));
        }
        return sb.toString();
    }

    private double generateRandomPrice() {
        // Generate a random price between 1.00 and 100.00 (inclusive)
        double randomPrice = Math.random() * 99 + 1;
        BigDecimal roundedPrice = new BigDecimal(randomPrice).setScale(2, BigDecimal.ROUND_HALF_UP);
        return roundedPrice.doubleValue();
    }
}