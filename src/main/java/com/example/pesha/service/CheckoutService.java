package com.example.pesha.service;

import com.example.pesha.dao.entity.*;
import com.example.pesha.dao.repositories.CartRepository;
import com.example.pesha.dao.repositories.UserRepository;
import com.example.pesha.dto.OrderRequestDTO;
import com.example.pesha.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class CheckoutService {

    @Autowired
    private final CartRepository cartRepository;

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final OrderService orderService;

    public CheckoutService(CartRepository cartRepository, UserRepository userRepository, OrderService orderService) {
        this.cartRepository = cartRepository;
        this.userRepository = userRepository;
        this.orderService = orderService;
    }


    public OrderEntity checkout(OrderRequestDTO orderRequestDTO) {

        User user = userRepository.findByUserName(orderRequestDTO.getUserName())
                .orElseThrow(() -> new NotFoundException("can't find user"));
        Cart cart = cartRepository.findByUser(user);
        String shippingAddress = orderRequestDTO.getShippingAddress();
        String paymentMethod = orderRequestDTO.getPaymentMethod();
        Double discount = orderRequestDTO.getDiscount();
        int totalPrice = orderRequestDTO.getTotalPrice();
        double finalPrice = totalPrice - discount;

        OrderEntity order = new OrderEntity();
        order.setUser(user);
        order.setShippingAddress(shippingAddress);
        order.setDiscount(discount);
        order.setPaymentMethod(paymentMethod);
        order.setTotalPrice(totalPrice);
        order.setFinalPrice(finalPrice);
        orderService.createOrder(order);

        order.setOrderItems(convertCartToOrderItems(order, cart));

        return orderService.createOrder(order);

    }

    private List<OrderItem> convertCartToOrderItems(OrderEntity order, Cart cart) {

        List<OrderItem> orderItems = new ArrayList<>();
        Map<Product, Integer> productQuantity = cart.getProductQuantity();

        for (Product product : cart.getProducts()) {
            int quantity = productQuantity.getOrDefault(product, 0);
            int price = product.getProductPrice();

            OrderItem orderItem = new OrderItem();
            orderItem.setProduct(product);
            orderItem.setQuantity(quantity);
            orderItem.setPrice(price);
            orderItem.setOrder(order);
            orderItems.add(orderItem);
        }

        return orderItems;

    }

}
