package com.example.pesha.controller;

import com.example.pesha.dao.entity.Cart;
import com.example.pesha.dao.entity.OrderEntity;
import com.example.pesha.dto.OrderRequestDTO;
import com.example.pesha.exception.NotFoundException;
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
    public OrderEntity checkout(@RequestBody OrderRequestDTO orderRequestDTO,Model model) {
        try {
            OrderEntity order = checkoutService.checkout(orderRequestDTO);
            model.addAttribute("order",order);
            model.addAttribute("orderRequestDto",orderRequestDTO);
            return order;
        } catch (NotFoundException e) {
            model.addAttribute("errorMessage", "User not found");
            return null;
        } catch (Exception e) {
            model.addAttribute("errorMessage", e);
            return null;
        }

    }

    @GetMapping("/checkout/{userName}")
    public String getCheckoutByUser(@PathVariable("userName") String userName, Model model) {
        try {
            Cart cart = cartService.getCartByUser(userName);
            model.addAttribute("cart", cart);
            model.addAttribute("userName", userName);
            return "checkout";
        } catch (NotFoundException e) {
            model.addAttribute("errorMessage", "User not found");
            return "cart";
        } catch (Exception e) {
            model.addAttribute("errorMessage", e);
            return "cart";
        }

    }

}
