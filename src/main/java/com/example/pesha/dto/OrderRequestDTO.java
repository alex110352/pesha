package com.example.pesha.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class OrderRequestDTO {
    private String userName;
    private String shippingAddress;
    private String paymentMethod;
    private Double discount;
    private int totalPrice;

}
