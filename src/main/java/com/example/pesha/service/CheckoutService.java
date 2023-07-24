package com.example.pesha.service;

import com.example.pesha.dao.entity.Cart;
import com.example.pesha.dao.entity.OrderEntity;
import com.example.pesha.dao.entity.OrderItem;
import com.example.pesha.dao.entity.Product;
import com.example.pesha.dao.repositories.CartRepository;
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
    private final OrderService orderService;

    public CheckoutService(CartRepository cartRepository, OrderService orderService) {
        this.cartRepository = cartRepository;
        this.orderService = orderService;
    }

    public double calculateTotalPrice(Cart cart) {
        return cart.getTotalPrice();
    }

    public OrderEntity checkout(Cart cart, String shippingAddress, double discount) {

        double totalPrice = calculateTotalPrice(cart);
        double finalPrice = totalPrice - discount;

        OrderEntity order = new OrderEntity();
        order.setUser(cart.getUser());
        order.setShippingAddress(shippingAddress);
        order.setDiscount(discount);
        order.setTotalPrice(totalPrice);
        order.setFinalPrice(finalPrice);
        orderService.createOrder(order);

        order.setOrderItems(convertCartToOrderItems(order,cart));

        return orderService.createOrder(order);

    }

    private List<OrderItem> convertCartToOrderItems(OrderEntity order,Cart cart) {

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
