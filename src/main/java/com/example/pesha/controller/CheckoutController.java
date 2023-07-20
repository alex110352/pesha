package com.example.pesha.controller;

import com.example.pesha.dao.entity.Cart;
import com.example.pesha.dao.entity.OrderEntity;
import com.example.pesha.service.CheckoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "checkout")
public class CheckoutController {

    @Autowired
    private final CheckoutService checkoutService;

    public CheckoutController(CheckoutService checkoutService) {
        this.checkoutService = checkoutService;
    }

    @PostMapping
    public OrderEntity checkout(@RequestBody Cart cart, String shippingAddress, double discount) {
        return checkoutService.checkout(cart, shippingAddress, discount);
    }

}
