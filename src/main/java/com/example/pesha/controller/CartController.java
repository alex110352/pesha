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

    @GetMapping
    public List<Cart> getAllCartByUser(@RequestParam("userName") String userName) {
        return cartService.getAllCartByUser(userName);
    }

    @GetMapping
    public Cart getCartByUser(@RequestParam("userName") String userName, @RequestParam("cartNumber") int cartNumber) {
        return cartService.getCartByUser(userName, cartNumber);
    }

    @PostMapping
    public Cart createCart(@RequestBody CartRequestDTO cartRequestDTO) {
        return cartService.createCart(cartRequestDTO);
    }

    @PutMapping
    public Cart replaceCart(@RequestParam("userName") String userName, @RequestParam("cartNumber") int cartNumber,
                            @RequestBody CartRequestDTO cartRequestDTO) {
        return cartService.replaceCart(userName, cartNumber, cartRequestDTO);
    }

    @DeleteMapping
    public void deleteCartByUser(@RequestParam("userName") String userName, @RequestParam("cartNumber") int cartNumber){
        cartService.deleteCartByUser(userName,cartNumber);
    }

}
