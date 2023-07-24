package com.example.pesha;

import com.example.pesha.controller.CartController;
import com.example.pesha.dao.entity.Authority;
import com.example.pesha.dao.entity.Cart;
import com.example.pesha.dao.entity.Product;
import com.example.pesha.dao.entity.User;
import com.example.pesha.dao.repositories.UserRepository;
import com.example.pesha.dto.CartRequestDTO;
import com.example.pesha.service.CartService;
import org.json.JSONArray;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = PeshaApplication.class)
public class CartControllerTest {

    private MockMvc cartMockMvc;

    @Autowired
    private CartController cartController;

    @MockBean
    private CartService cartService;

    @Autowired
    private UserRepository userRepository;


    @Before
    public void setup() throws Exception {
        this.cartMockMvc = standaloneSetup(this.cartController).build();

    }

    @Test
    public void testCartPostAPI() throws Exception {

        Product product1 = new Product("貓", 100);
        Product product2 = new Product("狗", 200);


        List<Product> productList = new ArrayList<>();
        productList.add(product1);
        productList.add(product2);


        Cart cart = new Cart();
        cart.setTotalPrice(1500);
        when(this.cartService.createCart(any(CartRequestDTO.class)))
                .thenReturn(cart);

        JSONArray jsonProductId = new JSONArray();
        jsonProductId.put(new JSONObject().put("productId", 1L));
        jsonProductId.put(new JSONObject().put("productId", 2L));

        Map<Product,Integer> productQuantity = new HashMap<>();
        productQuantity.put(product1,3);
        productQuantity.put(product2,5);

        JSONObject jsonProductQuantity = new JSONObject();
        for (Map.Entry<Product, Integer> entry : productQuantity.entrySet()) {
            Product product = entry.getKey();
            int quantity = entry.getValue();
            jsonProductQuantity.put(product.getProductName(), quantity);
        }

        JSONObject request = new JSONObject()
                .put("userId", 1L)
                .put("productIds", jsonProductId)
                .put("productQuantity", jsonProductQuantity);




        cartMockMvc.perform(MockMvcRequestBuilders
                        .post("/cart")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request.toString()))

                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.totalPrice").value(1300));

    }

    @Test
    public void testCartGetAPI() throws Exception {

        List<Authority> authorities = new ArrayList<>();
        authorities.add(new Authority("admin"));
        authorities.add(new Authority("normal"));
        User user = new User("tony","1234",authorities);
        userRepository.save(user);

        Product product1 = new Product("貓", 100);
        Product product2 = new Product("狗", 200);


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


        when(this.cartService.getCartByUser(anyLong()))
                .thenReturn(cart);

        cartMockMvc.perform(MockMvcRequestBuilders
                        .get("/cart")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("userName","tony"))

                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].totalPrice").value(1300));

    }

}
