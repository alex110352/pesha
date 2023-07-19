package com.example.pesha.service;

import com.example.pesha.dao.entity.Cart;
import com.example.pesha.dao.entity.Product;
import com.example.pesha.dao.entity.User;
import com.example.pesha.dao.repositories.CartRepository;
import com.example.pesha.dao.repositories.ProductRepository;
import com.example.pesha.dao.repositories.UserRepository;
import com.example.pesha.dto.CartRequestDTO;
import com.example.pesha.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

        User user = userRepository.findById(cartRequestDTO.getUserId())
                .orElseThrow(() -> new NotFoundException("user not found"));

        List<Product> productList = productRepository.findAllById(cartRequestDTO.getProductIds());

        Cart cart = new Cart();
        cart.setUser(user);
        cart.setProducts(productList);
        cart.setProductQuantity(cartRequestDTO.getProductQuantity());
        cart.calculatePrices();

        return cartRepository.save(cart);

    }

    public List<Cart> getAllCartByUser(String userName) {
        User user = userRepository.findByUserName(userName)
                .orElseThrow(() -> new NotFoundException("not found user"));
        return cartRepository.findByUser(user);
    }

    public Cart getCartByUser(String userName, int cartNumber) {
        User user = userRepository.findByUserName(userName)
                .orElseThrow(() -> new NotFoundException("not found user"));
        List<Cart> cartList = cartRepository.findByUser(user);
        if (cartNumber < 0 || cartNumber >= cartList.size()) {
            throw new IndexOutOfBoundsException("Invalid cart number");
        }
        return cartList.get(cartNumber);
    }

    public Cart replaceCart(String userName, int cartNumber, CartRequestDTO cartRequestDTO) {

        User user = userRepository.findByUserName(userName)
                .orElseThrow(() -> new NotFoundException("not found user"));

        List<Cart> cartList = cartRepository.findByUser(user);
        if (cartNumber < 0 || cartNumber >= cartList.size()) {
            throw new IndexOutOfBoundsException("Invalid cart number");
        }

        Cart cart = cartList.get(cartNumber);
        List<Product> productList = productRepository.findAllById(cartRequestDTO.getProductIds());
        cart.setProducts(productList);
        cart.setProductQuantity(cartRequestDTO.getProductQuantity());
        cart.calculatePrices();
        return cartRepository.save(cart);
    }

    public void deleteCartByUser(String userName, int cartNumber) {

        User user = userRepository.findByUserName(userName)
                .orElseThrow(() -> new NotFoundException("not found user"));

        List<Cart> cartList = cartRepository.findByUser(user);
        if (cartNumber < 0 || cartNumber >= cartList.size()) {
            throw new IndexOutOfBoundsException("Invalid cart number");
        }

        Cart cart = cartList.get(cartNumber);
        cartRepository.delete(cart);

    }
}
