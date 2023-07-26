package com.example.pesha.dao.repositories;

import com.example.pesha.dao.entity.OrderItem;
import com.example.pesha.dao.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem,Long> {
    List<OrderItem> findByProduct(Product product);
}
