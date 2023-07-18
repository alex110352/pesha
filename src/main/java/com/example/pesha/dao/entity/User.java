package com.example.pesha.dao.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    @ManyToMany(cascade = CascadeType.ALL)
    private List<Authority> authorities;

    public User(String userName, String userPassword, List<Authority> authorities) {
        this.userName = userName;
        this.userPassword = userPassword;
        this.authorities = authorities;
    }
}
