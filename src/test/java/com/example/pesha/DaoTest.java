package com.example.pesha;

import com.example.pesha.dao.entity.Authority;
import com.example.pesha.dao.entity.Product;
import com.example.pesha.dao.entity.User;
import com.example.pesha.dao.repositories.AuthorityRepository;
import com.example.pesha.dao.repositories.ProductRepository;
import com.example.pesha.dao.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class DaoTest {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    AuthorityRepository authorityRepository;

    @Test
    @Commit
    @Transactional
    public void getProductPrice() {
        Product product = new Product();
        product.setProductName("A02");
        product.setProductPrice(400);
        productRepository.save(product);
        System.out.println(productRepository.findById(16L).orElse(null).getProductPrice());
    }

    @Test
    @Commit
    @Transactional
    public void createUser() {

        List<Authority> authorities = new ArrayList<>();
        authorities.add(new Authority("admin"));
        authorities.add(new Authority("normal"));

        User user = new User();
        user.setUserName("tony");
        user.setUserPassword("1234");
        user.setAuthorities(authorities);

        userRepository.save(user);
    }

    @Test
    @Commit
    @Transactional
    public void replaceUser() {


        List<Authority> authorities = new ArrayList<>();
        authorities.add(new Authority("admin"));
        authorities.add(new Authority("normal"));

        User user = new User();
        user.setUserName("tony");
        user.setUserPassword("1234");
        user.setAuthorities(authorities);

        userRepository.save(user);

        List<Authority> replaceAuthorities = new ArrayList<>();
        replaceAuthorities.add(new Authority("admin"));
        replaceAuthorities.add(new Authority("employee"));

        User replaceUser = userRepository.findByUserName("tony").orElseThrow();
        replaceUser.setUserName("hsuan");
        replaceUser.setUserPassword("5678");
        replaceUser.setAuthorities(replaceAuthorities);

        userRepository.save(replaceUser);

    }

}
