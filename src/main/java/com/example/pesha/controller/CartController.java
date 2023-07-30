package com.example.pesha.controller;

import com.example.pesha.dao.entity.Cart;
import com.example.pesha.dto.AddToCartRequestDTO;
import com.example.pesha.dto.CartRequestDTO;
import com.example.pesha.exception.NotFoundException;
import com.example.pesha.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
        try {
            Cart cart = cartService.getCartByUser(userName);
            model.addAttribute("cart", cart);
            model.addAttribute("userName", userName);
            return "cart";
        } catch (NotFoundException e) {
            model.addAttribute("errorMessage", "User not found");
            return "all_products";
        } catch (Exception e) {
            model.addAttribute("errorMessage", e);
            return "all_products";
        }
    }

    @PostMapping("/add")
    @ResponseBody
    public Cart addToCart(@RequestBody AddToCartRequestDTO addToCartRequestDTO, Model model) {
        try {
            Cart cart = cartService.addToCart(addToCartRequestDTO);
            model.addAttribute("cart", cart);
            return cart;
        } catch (NotFoundException e) {
            model.addAttribute("errorMessage", "can't find" + e);
            return null;
        } catch (Exception e) {
            model.addAttribute("errorMessage", e);
            return null;
        }

    }

    @PutMapping("/replace/{userName}")
    @ResponseBody
    public Cart updateCartQuantity(@RequestBody AddToCartRequestDTO addToCartRequestDTO, Model model) {
        try {
            Cart cart = cartService.updateCartQuantity(addToCartRequestDTO);
            model.addAttribute("cart", cart);
            return cart;
        } catch (NotFoundException e) {
            model.addAttribute("errorMessage", "can't find" + e);
            return null;
        } catch (Exception e) {
            model.addAttribute("errorMessage", e);
            return null;
        }
    }


    @DeleteMapping("/delete/{userName}/{productId}")
    @ResponseBody
    public Cart deleteCartByProduct(@PathVariable String userName, @PathVariable Long productId, Model model) {
        try {
            Cart cart = cartService.deleteCartByProduct(userName, productId);
            model.addAttribute("cart", cart);
            return cart;
        } catch (NotFoundException e) {
            model.addAttribute("errorMessage", "can't find" + e);
            return null;
        } catch (Exception e) {
            model.addAttribute("errorMessage", e);
            return null;
        }
    }

}
