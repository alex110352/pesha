package com.example.pesha.controller;

import com.example.pesha.dao.entity.Cart;
import com.example.pesha.dto.CartRequestDTO;
import com.example.pesha.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "cart")
public class CartController {

    @Autowired
    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }


    @GetMapping("/{userId}")
    public Cart getCartByUser(@PathVariable("userId") Long userId) {
        return cartService.getCartByUser(userId);
    }

    @PostMapping
    public Cart createCart(@RequestBody CartRequestDTO cartRequestDTO) {
        return cartService.createCart(cartRequestDTO);
    }

    @PutMapping
    public Cart replaceCart(@RequestBody CartRequestDTO cartRequestDTO) {
        return cartService.replaceCart(cartRequestDTO);
    }

    @DeleteMapping
    public void deleteCartByUser(@RequestParam("userId") Long userId){
        cartService.deleteCartByUser(userId);
    }

}
