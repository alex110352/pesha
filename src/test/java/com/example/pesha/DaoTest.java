package com.example.pesha;

import com.example.pesha.dao.entity.Product;
import com.example.pesha.dao.repositories.ProductRepository;
import jakarta.transaction.Transactional;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class DaoTest {

    @Autowired
    ProductRepository productRepository;

    @Test
    @Commit
    @Transactional
    public void getProductPrice(){
        Product product = new Product();
        product.setProductName("A02");
        product.setProductPrice(400);
        productRepository.save(product);
        System.out.println(productRepository.findById(16L).orElse(null).getProductPrice());
    }

}
