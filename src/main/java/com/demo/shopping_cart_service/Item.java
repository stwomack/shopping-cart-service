package com.demo.shopping_cart_service;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Item {
    private String skuCode;
    private double price;
    private int quantity;
}
