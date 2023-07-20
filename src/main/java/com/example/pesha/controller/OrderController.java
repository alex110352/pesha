package com.example.pesha.controller;

import com.example.pesha.dao.entity.OrderEntity;
import com.example.pesha.dao.entity.User;
import com.example.pesha.service.OrderService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "order")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderController {

    @Autowired
    private OrderService orderService;
    @GetMapping("/{orderId}")
    public OrderEntity getOrder(@PathVariable("orderId")Long orderId){
        return orderService.getOrder(orderId);
    }

    @GetMapping("/{userName}")
    public List<OrderEntity> getOrderByUser(@PathVariable("userName")String userName){
        return orderService.getOrderByUser(userName);
    }

    @DeleteMapping("/{orderId}")
    public void deleteOrder(@PathVariable("orderId")Long orderId){
        orderService.deleteOrder(orderId);
    }
}
