package com.example.pesha.controller;

import com.example.pesha.dao.entity.OrderEntity;
import com.example.pesha.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(value = "order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/{userName}/{orderId}")
    public OrderEntity getOrder(@PathVariable("orderId") Long orderId) {
        return orderService.getOrder(orderId);
    }

    @GetMapping("/{userName}")
    public String getOrderByUser(@PathVariable("userName") String userName, Model model) {
        List<OrderEntity> orderList = orderService.getOrderByUser(userName);
        model.addAttribute("orderList", orderList);
        model.addAttribute("userName", userName);
        return "order";
    }

    @GetMapping("/admin/all")
    public List<OrderEntity> getAllOrder() {
        return orderService.getAllOrder();
    }

    @DeleteMapping
    public void deleteOrder(@RequestParam("orderId") Long orderId) {
        orderService.deleteOrder(orderId);
    }
}
