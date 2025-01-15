package com.demo.shopping_cart_service;

import com.demo.shopping_cart_service.config.ObjectMapperConfig;
import com.demo.shopping_cart_service.domain.AnyObject;
import com.demo.shopping_cart_service.domain.Item;
import com.demo.shopping_cart_service.domain.ShoppingCart;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@EnableScheduling
public class ShoppingCartService {
    private final Logger log = LoggerFactory.getLogger(ShoppingCartService.class);

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ShoppingCartRepository cartRepository;

    @Scheduled(fixedRate = 5000) // Generate new carts every 5 seconds
    public void generateRandomCart() throws JsonProcessingException {
        String userId = UUID.randomUUID().toString();
        ShoppingCart cart = new ShoppingCart(UUID.randomUUID().toString(), generateRandomItems());
        log.info("Adding Cart: {}", cart.toString());
//        redisTemplate.opsForValue().set(cart.getUserId(), cart);
//        cartRepository.save(cart);
        redisTemplate.getConnectionFactory().
                getConnection().
                execute("JSON.SET",
                        userId.getBytes(StandardCharsets.UTF_8),
                        "$".getBytes(StandardCharsets.UTF_8),
                        objectMapper.writeValueAsString(cart).getBytes(StandardCharsets.UTF_8));
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
        String localString = "Test";
        List<String> localList = new ArrayList<String>();
        localList.add(localString);
        AnyObject anyObject = new AnyObject(generateRandomSkuCode(), (int) (Math.random() * 5) + 1, localList);
        for (int i = 0; i < numItems; i++) {
            items.add(new Item(
                    generateRandomSkuCode(),
                    generateRandomPrice(),
                    (int) (Math.random() * 5) + 1, // Random quantity
                    anyObject
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