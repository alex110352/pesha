package com.example.pesha.controller;

import com.example.pesha.dao.entity.Cart;
import com.example.pesha.dao.entity.OrderEntity;
import com.example.pesha.dto.OrderRequestDTO;
import com.example.pesha.service.CartService;
import com.example.pesha.service.CheckoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class CheckoutController {

    @Autowired
    private final CheckoutService checkoutService;

    @Autowired
    private final CartService cartService;


    public CheckoutController(CheckoutService checkoutService, CartService cartService, OrderRequestDTO orderRequestDTO) {
        this.checkoutService = checkoutService;
        this.cartService = cartService;
    }

    @PostMapping("/checkout")
    public OrderEntity checkout(@RequestBody OrderRequestDTO orderRequestDTO) {
        return checkoutService.checkout(orderRequestDTO);
    }

    @GetMapping("/checkout/{userName}")
    public String getCheckoutByUser(@PathVariable("userName") String userName, Model model) {
        Cart cart = cartService.getCartByUser(userName);
        model.addAttribute("cart", cart);
        model.addAttribute("userName", userName);
        return "checkout";
    }

}
