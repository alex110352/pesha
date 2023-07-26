package com.example.pesha.service;

import com.example.pesha.dao.entity.OrderEntity;
import com.example.pesha.dao.entity.User;
import com.example.pesha.dao.repositories.OrderRepository;
import com.example.pesha.dao.repositories.UserRepository;
import com.example.pesha.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    @Autowired
    private final OrderRepository orderRepository;

    @Autowired
    private final UserRepository userRepository;

    public OrderService(OrderRepository orderRepository, UserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
    }

    public OrderEntity createOrder(OrderEntity order){
        return orderRepository.save(order);
    }

    public OrderEntity getOrder(Long orderId) {

        OrderEntity order = orderRepository.findById(orderId)
                .orElseThrow(() -> new NotFoundException("can't find order"));
        return order;
    }

    public List<OrderEntity> getOrderByUser(String userName) {

        User user = userRepository.findByUserName(userName)
                .orElseThrow(() -> new NotFoundException("can't find user"));

        return orderRepository.findAllByUser(user)
                .orElseThrow(() -> new NotFoundException("empty order"));
    }

    public void deleteOrder(Long orderId) {
        OrderEntity order = orderRepository.findById(orderId)
                .orElseThrow(() -> new NotFoundException("can't find order"));
        orderRepository.delete(order);
    }

    public List<OrderEntity> getAllOrder() {
        List<OrderEntity> orderList = orderRepository.findAll();
        if (orderList.isEmpty()) {
            throw  new NotFoundException("can't find order");
        }
        return orderList;
    }
}
