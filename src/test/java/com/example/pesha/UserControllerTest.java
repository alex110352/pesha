package com.example.pesha;

import com.example.pesha.controller.UserController;
import com.example.pesha.dao.entity.Authority;
import com.example.pesha.dao.entity.User;
import com.example.pesha.service.UserService;
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
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = PeshaApplication.class)
public class UserControllerTest {

    private MockMvc userMockMvc;

    @Autowired
    private UserController userController;

    @MockBean
    private UserService userService;


    @Before
    public void setup() throws Exception {
        this.userMockMvc = standaloneSetup(this.userController).build();
    }

    @Test
    public void testUserPostAPI() throws Exception {

        List<Authority> authorities = new ArrayList<>();
        authorities.add(new Authority("admin"));
        authorities.add(new Authority("normal"));

        when(this.userService.createUser(any(User.class)))
                .thenReturn(new User("tony", "1234", authorities));

        JSONArray jsonAuthorities = new JSONArray();
        jsonAuthorities.put(new JSONObject().put("authority", "admin"));
        jsonAuthorities.put(new JSONObject().put("authority", "normal"));

        JSONObject request = new JSONObject()
                .put("userName", "tony")
                .put("userPassword", "1234")
                .put("authorities", jsonAuthorities);

        userMockMvc.perform(MockMvcRequestBuilders
                        .post("/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request.toString()))

                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.userName").value("tony"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.userPassword").value(1234))
                .andExpect(MockMvcResultMatchers.jsonPath("$.authorities[0].authority").value("admin"));

    }

    @Test
    public void testUserGetAPI() throws Exception {

        List<Authority> authorities = new ArrayList<>();
        authorities.add(new Authority("admin"));
        authorities.add(new Authority("normal"));

        when(this.userService.getUser(anyLong()))
                .thenReturn(new User("tony", "1234", authorities));


        userMockMvc.perform(MockMvcRequestBuilders
                        .get("/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("userName", "tony"))

                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.userName").value("tony"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.userPassword").value(1234))
                .andExpect(MockMvcResultMatchers.jsonPath("$.authorities[0].authority").value("admin"));

    }

    @Test
    public void testUserPutAPI() throws Exception {

        List<Authority> authorities = new ArrayList<>();
        authorities.add(new Authority("admin"));
        authorities.add(new Authority("employee"));

        when(this.userService.replaceUser(anyLong(), any(User.class)))
                .thenReturn(new User("hsuan", "5678", authorities));

        JSONArray jsonAuthorities = new JSONArray();
        jsonAuthorities.put(new JSONObject().put("authority", "admin"));
        jsonAuthorities.put(new JSONObject().put("authority", "employee"));


        JSONObject request = new JSONObject()
                .put("userName", "hsuan")
                .put("userPassword", "5678")
                .put("authorities", jsonAuthorities);

        userMockMvc.perform(MockMvcRequestBuilders
                        .put("/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request.toString())
                        .param("userName", "tony")
                        .param("userPassword", "1234"))


                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.userName").value("hsuan"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.userPassword").value("5678"));

    }

}
