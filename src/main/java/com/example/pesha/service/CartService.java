package com.example.pesha.service;

import com.example.pesha.dao.entity.Cart;
import com.example.pesha.dao.entity.Product;
import com.example.pesha.dao.entity.User;
import com.example.pesha.dao.repositories.CartRepository;
import com.example.pesha.dao.repositories.ProductRepository;
import com.example.pesha.dao.repositories.UserRepository;
import com.example.pesha.dto.AddToCartRequestDTO;
import com.example.pesha.dto.CartRequestDTO;
import com.example.pesha.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CartService {

    @Autowired
    private final CartRepository cartRepository;
    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final ProductRepository productRepository;

    public CartService(CartRepository cartRepository, UserRepository userRepository, ProductRepository productRepository) {
        this.cartRepository = cartRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }

    public Cart createCart(CartRequestDTO cartRequestDTO) {

        User user = userRepository.findById(cartRequestDTO.getUserId()).orElseThrow(() -> new NotFoundException("user not found"));

        List<Product> productList = productRepository.findAllById(cartRequestDTO.getProductIds());

        Cart cart = new Cart();
        cart.setUser(user);
        cart.setProducts(productList);
        cart.setProductQuantity(cartRequestDTO.getProductQuantity());
        cart.calculatePrices();
        return cartRepository.save(cart);
    }

    public Cart addToCart(AddToCartRequestDTO addToCartRequestDTO) {

        User user = userRepository.findByUserName(addToCartRequestDTO.getUserName()).orElseThrow(() -> new NotFoundException("user not found"));

        Cart cart = cartRepository.findByUser(user);

        Product product = productRepository.findById(addToCartRequestDTO.getProductId()).orElseThrow(() -> new NotFoundException("product not found"));

        List<Product> productList;
        Map<Product, Integer> productQuantity;

        if (cart == null) {
            cart = new Cart();
            productList = new ArrayList<>();
            productQuantity = new HashMap<>();
        } else {
            productList = cart.getProducts();
            productQuantity = cart.getProductQuantity();
        }

        cart.setUser(user);

        int quantity = productQuantity.getOrDefault(product, 0);
        quantity++;
        productQuantity.put(product, quantity);
        cart.setProductQuantity(productQuantity);

        if (!productList.contains(product)) {
            productList.add(product);
        }
        cart.setProducts(productList);

        cart.calculatePrices();

        return cartRepository.save(cart);

    }


    public Cart getCartByUser(String userName) {
        User user = userRepository.findByUserName(userName)
                .orElseThrow(() -> new NotFoundException("not found user"));
        Cart cart = cartRepository.findByUser(user);
        cart.calculatePrices();

        return cart;
    }


    public Cart replaceCart(CartRequestDTO cartRequestDTO) {

        User user = userRepository.findById(cartRequestDTO.getUserId()).orElseThrow(() -> new NotFoundException("not found user"));

        Cart cart = cartRepository.findByUser(user);

        List<Product> productList = productRepository.findAllById(cartRequestDTO.getProductIds());
        cart.setProducts(productList);
        cart.setProductQuantity(cartRequestDTO.getProductQuantity());
        cart.calculatePrices();
        return cartRepository.save(cart);
    }

    public void deleteCartByUser(Long userId) {

        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("not found user"));

        Cart cart = cartRepository.findByUser(user);
        cartRepository.delete(cart);

    }

    public Cart updateCartQuantity(AddToCartRequestDTO addToCartRequestDTO) {
        User user = userRepository.findByUserName(addToCartRequestDTO.getUserName()).orElseThrow(() -> new NotFoundException("user not found"));

        Product product = productRepository.findById(addToCartRequestDTO.getProductId()).orElseThrow(() -> new NotFoundException("product not found"));

        Cart cart = cartRepository.findByUser(user);

        Map<Product, Integer> productQuantity = cart.getProductQuantity();
        int replaceQuantity = addToCartRequestDTO.getProductQuantity();
        productQuantity.put(product, replaceQuantity);
        cart.setProductQuantity(productQuantity);
        cart.calculatePrices();
        return cartRepository.save(cart);
    }


    public Cart deleteCartByProduct(String userName, Long productId) {

        User user = userRepository.findByUserName(userName).orElseThrow(() -> new NotFoundException("user not found"));

        Cart cart = cartRepository.findByUser(user);

        Product product = productRepository.findById(productId).orElseThrow(() -> new NotFoundException("product not found"));

        List<Product> productList = cart.getProducts();
        Map<Product, Integer> productQuantity = cart.getProductQuantity();

        cart.setUser(user);

        productQuantity.remove(product);
        cart.setProductQuantity(productQuantity);

        productList.remove(product);
        cart.setProducts(productList);

        cart.calculatePrices();

        return cartRepository.save(cart);
    }
}
