package com.example.pesha;

import com.example.pesha.dao.entity.Product;
import com.example.pesha.dao.repositories.ProductRepository;
import com.example.pesha.service.ProductService;
import jakarta.transaction.Transactional;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = PeshaApplication.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ServiceTest {

    @Autowired
    ProductService productService;
    @MockBean
    ProductRepository productRepository;

    @Test
    @Transactional
    public void getProductPrice() {
        Product productSaved = new Product("D01", 100);
        when(productRepository.save(any(Product.class))).thenReturn(productSaved);

        Product productRequest = new Product("D01", 100);
        Product product = productService.createProduct(productRequest);
        System.out.println(product.getProductPrice());
    }

}
