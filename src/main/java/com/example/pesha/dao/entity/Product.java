package com.example.pesha.dao.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "product")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String productName;
    private int productPrice;
    @ManyToMany(mappedBy = "products")
    @JsonIgnore
    private List<Cart> carts;

    @OneToOne(mappedBy = "product")
    private OrderItem orderItem;


    public Product(String productName, int productPrice) {
        this.productName = productName;
        this.productPrice = productPrice;
    }
}
