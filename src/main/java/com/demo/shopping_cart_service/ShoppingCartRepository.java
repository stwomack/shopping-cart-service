package com.demo.shopping_cart_service;

import com.demo.shopping_cart_service.domain.ShoppingCart;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShoppingCartRepository extends CrudRepository<ShoppingCart, String> {
}
