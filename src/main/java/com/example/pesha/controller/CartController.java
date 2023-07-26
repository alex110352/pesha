package com.example.pesha.controller;

import com.example.pesha.dao.entity.Cart;
import com.example.pesha.dao.entity.Product;
import com.example.pesha.dto.AddToCartRequestDTO;
import com.example.pesha.dto.CartRequestDTO;
import com.example.pesha.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "cart")
public class CartController {

    @Autowired
    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }


    @GetMapping("/{userName}")
    public String getCartByUser(@PathVariable("userName") String userName, Model model) {

        Cart cart = cartService.getCartByUser(userName);
        System.out.println(cart);
        model.addAttribute("cart", cart);
        model.addAttribute("userName", userName);
        return "cart";
    }


    @PostMapping
    @ResponseBody
    public Cart createCart(@RequestBody CartRequestDTO cartRequestDTO) {
        return cartService.createCart(cartRequestDTO);
    }
    @PostMapping("/add")
    @ResponseBody
    public Cart addToCart(@RequestBody AddToCartRequestDTO addToCartRequestDTO) {
        return cartService.addToCart(addToCartRequestDTO);
    }

    @PutMapping
    @ResponseBody
    public Cart replaceCart(@RequestBody CartRequestDTO cartRequestDTO) {
        return cartService.replaceCart(cartRequestDTO);
    }

    @PutMapping("/replace/{userName}")
    @ResponseBody
    public Cart updateCartQuantity(@RequestBody AddToCartRequestDTO addToCartRequestDTO) {
        return cartService.updateCartQuantity(addToCartRequestDTO);
    }

    @DeleteMapping
    public void deleteCartByUser(@RequestParam("userId") Long userId){
        cartService.deleteCartByUser(userId);
    }
    @DeleteMapping("/delete/{userName}/{productId}")
    @ResponseBody
    public Cart deleteCartByProduct(@PathVariable String userName, @PathVariable Long productId){
        return cartService.deleteCartByProduct(userName,productId);
    }



}
