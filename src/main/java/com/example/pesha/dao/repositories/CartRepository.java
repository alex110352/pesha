package com.example.pesha.dao.repositories;

import com.example.pesha.dao.entity.Cart;
import com.example.pesha.dao.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart,Long> {
    List<Cart> findByUser(User user);
}
