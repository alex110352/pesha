package com.example.pesha.dao.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity
@Table(name = "cart")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    private List<Product> products;


    @ElementCollection
    @CollectionTable(name = "product_quantity", joinColumns = @JoinColumn(name = "cart_id"))
    @MapKeyJoinColumn(name = "product_id")
    @Column(name = "quantity")
    @JsonIgnore
    private Map<Product, Integer> productQuantity;

    @Transient
    @Column(name = "product_price")
    private Map<String, Integer> itemPrices;
    @Transient
    @Column(name = "total_price")
    private int totalPrice;

    /*public Map<Product, Integer> getProductQuantity() {
        Map<Product, Integer> productQuantity = new HashMap<>();
        for (Product product : products) {
            int quantity = this.productQuantity.getOrDefault(product, 0);
            productQuantity.put(product, quantity);
        }
        return productQuantity;
    }

     */

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
