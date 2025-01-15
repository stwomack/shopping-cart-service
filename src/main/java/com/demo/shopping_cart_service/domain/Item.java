package com.demo.shopping_cart_service.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Item {
    private String skuCode;
    private double price;
    private int quantity;
    private AnyObject anyObject;
}
