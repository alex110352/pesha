package com.example.pesha.dao.repositories;

import com.example.pesha.dao.entity.OrderEntity;
import com.example.pesha.dao.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity,Long> {
    Optional<List<OrderEntity>> findAllByUser(User user);
}
