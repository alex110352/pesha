package com.example.pesha.dao.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "user")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String userName;
    private String userPassword;
    @OneToOne(mappedBy = "user",cascade = CascadeType.ALL,orphanRemoval = true)
    private Cart cart;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Authority> authorities;

    @OneToMany(mappedBy = "user",cascade = CascadeType.REMOVE,orphanRemoval = true)
    private List<OrderEntity> orderEntityList;

    public User(String userName, String userPassword, List<Authority> authorities) {
        this.userName = userName;
        this.userPassword = userPassword;
        this.authorities = authorities;
    }
}
