package com.example.pesha.dao.repositories;

import com.example.pesha.dao.entity.Cart;
import com.example.pesha.dao.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart,Long> {
    Cart findByUser(User user);
}
