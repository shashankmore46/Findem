package org.example.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    private String orderId;
    private String productName;
    private String category;
    private int quantity;
    private float unitPrice;
    private float discountPercent;
    private String region;
    private LocalDate saleDate;
    private String customerEmail;
    private float revenue;
}
