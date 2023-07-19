package com.example.pesha.dao.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity
@Table(name = "cart")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToMany(cascade = CascadeType.ALL)
    private List<Product> products;
    @ElementCollection
    @CollectionTable(name = "product_quantity", joinColumns = @JoinColumn(name = "cart_id"))
    @MapKeyJoinColumn(name = "product_id")
    @Column(name = "quantity")
    private Map<Product, Integer> productQuantity;
    @Transient
    @Column(name = "product_price")
    private Map<String, Integer> itemPrices;
    @Transient
    @Column(name = "total_price")
    private int totalPrice;
    public void calculatePrices() {

        itemPrices = new HashMap<>();
        totalPrice = 0;
        for (Product product : products) {
            int quantity = productQuantity.getOrDefault(product, 0);
            int price = product.getProductPrice();
            itemPrices.put(product.getProductName(), price);
            totalPrice = quantity * price + totalPrice;
        }


    }
}
