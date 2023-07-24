package com.example.pesha.controller;

import com.example.pesha.dao.entity.OrderEntity;
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

    @GetMapping("/user/{userId}")
    public List<OrderEntity> getOrderByUser(@PathVariable("userId")Long userId){
        return orderService.getOrderByUser(userId);
    }

    @GetMapping("/admin/all")
    public List<OrderEntity> getAllOrder(){
        return orderService.getAllOrder();
    }

    @DeleteMapping
    public void deleteOrder(@RequestParam("orderId")Long orderId){
        orderService.deleteOrder(orderId);
    }
}
