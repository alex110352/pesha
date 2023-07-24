package com.example.pesha;

import com.example.pesha.dao.entity.*;
import com.example.pesha.dao.repositories.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class DaoTest {

    @Autowired
    ProductRepository productRepository;
    @Autowired
    AuthorityRepository authorityRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    CartRepository cartRepository;


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

    @Test
    @Commit
    @Transactional
    public void createOrder() {

        List<Authority> authorities = new ArrayList<>();
        authorities.add(new Authority("admin"));
        authorities.add(new Authority("normal"));
        authorities.add(new Authority("ROLE_manager"));
        authorityRepository.saveAll(authorities);

        PasswordEncoder pe = new BCryptPasswordEncoder();

        User user = new User();
        user.setUserName("tony");
        user.setUserPassword(pe.encode("1234"));
        user.setAuthorities(authorities);

        userRepository.save(user);

        Product product1 = new Product("貓", 100);
        Product product2 = new Product("狗", 200);
        productRepository.save(product1);
        productRepository.save(product2);


        List<Product> productList = new ArrayList<>();
        productList.add(product1);
        productList.add(product2);

        Map<Product, Integer> productQuantity = new HashMap<>();
        productQuantity.put(product1, 3);
        productQuantity.put(product2, 5);


        Cart cart = new Cart();
        cart.setProducts(productList);
        cart.setUser(user);
        cart.setProductQuantity(productQuantity);
        cart.calculatePrices();
        cartRepository.save(cart);

        int discount = 100;

        double totalPrice = cart.getTotalPrice();
        double finalPrice = totalPrice - discount;

        OrderEntity order = new OrderEntity();

        order.setUser(cart.getUser());
        order.setShippingAddress("abc-def");
        order.setDiscount(discount);
        order.setTotalPrice(totalPrice);
        order.setFinalPrice(finalPrice);
        orderRepository.save(order);

        List<OrderItem> orderItems = new ArrayList<>();
        Map<Product, Integer> productQuantitytest = cart.getProductQuantity();

        for (Product product : cart.getProducts()) {
            int quantity = productQuantitytest.getOrDefault(product, 0);
            int price = product.getProductPrice();

            OrderItem orderItem = new OrderItem();
            orderItem.setProduct(product);
            orderItem.setQuantity(quantity);
            orderItem.setPrice(price);
            orderItem.setOrder(order);
            orderItems.add(orderItem);
        }

        order.setOrderItems(orderItems);
        orderRepository.save(order);

    }

    @Test
    @Commit
    @Transactional
    public void testtt() {

        List<Authority> authorities = new ArrayList<>();
        authorities.add(new Authority("admin"));
        authorities.add(new Authority("normal"));
        authorities.add(new Authority("ROLE_manager"));
        PasswordEncoder pe = new BCryptPasswordEncoder();

        User user = new User();
        user.setUserName("tony");
        user.setUserPassword(pe.encode("1234"));
        user.setAuthorities(authorities);
        System.out.println(user.getUserPassword());
        userRepository.save(user);

    }

}
