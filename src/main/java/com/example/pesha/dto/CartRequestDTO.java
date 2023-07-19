package com.example.pesha.dto;

import com.example.pesha.dao.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartRequestDTO {
    private Long userId;
    private List<Long> productIds;
    private Map<Product, Integer> productQuantity;
}
