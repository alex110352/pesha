package com.example.pesha;

import com.example.pesha.controller.ProductController;
import com.example.pesha.dao.entity.Product;
import com.example.pesha.service.ProductService;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = PeshaApplication.class)
public class ProductControllerTest {


    private MockMvc productMockMvc;

    @Autowired
    private ProductController productController;
    @MockBean
    private ProductService productService;

    private Product product;

    @Before
    public void setup() throws Exception {
        this.productMockMvc = standaloneSetup(this.productController).build();

        product = new Product("A01", 100);
    }

    @Test
    public void testPostAPI() throws Exception {

        when(this.productService.createProduct(any(Product.class))).thenReturn(new Product("C01", 100));
        JSONObject request = new JSONObject()
                .put("productName", "C01")
                .put("productPrice", 100);

        productMockMvc.perform(MockMvcRequestBuilders
                        .post("/product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request.toString()))

                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.productName").value("C01"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.productPrice").value(100));

    }

    @Test
    public void testGetAPI() throws Exception {

        when(this.productService.getProduct("C01")).thenReturn(new Product("C01", 100));


        productMockMvc.perform(MockMvcRequestBuilders
                        .get("/product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("productName", "C01"))

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.productName").value("C01"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.productPrice").value(100));

    }

    @Test
    public void testGetAllAPI() throws Exception {

        List<Product> listProduct = new ArrayList<>(2);
        listProduct.add(new Product("A01", 100));
        listProduct.add(new Product("A02", 200));
        when(this.productService.getAllProduct()).thenReturn(listProduct);

        productMockMvc.perform(MockMvcRequestBuilders
                        .get("/product/all")
                        .contentType(MediaType.APPLICATION_JSON))

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].productName").value("A01"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].productPrice").value(100))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].productName").value("A02"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].productPrice").value(200));
        ;

    }

    @Test
    public void testPutAPI() throws Exception {

        when(this.productService.replaceProduct(anyString(), any(Product.class))).thenReturn(new Product("B01", 300));
        JSONObject request = new JSONObject()
                .put("productName", "B01")
                .put("productPrice", 300);

        productMockMvc.perform(MockMvcRequestBuilders
                        .put("/product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request.toString())
                        .param("replaceProductName", "A01"))

                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.productName").value("B01"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.productPrice").value(300));

    }


}
