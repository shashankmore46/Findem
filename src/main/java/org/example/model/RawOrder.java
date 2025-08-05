package org.example.model;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.ingestor.validator.QuantityValidator;
import org.example.ingestor.validator.Validator;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class RawOrder {
    private String orderId;
    private String productName;
    private String category;

    @Validator(handler = QuantityValidator.class)
    private String quantity;
    private Float unitPrice;
    private Float discountPercent;
    private String region;
    private String saleDate;
    private String customerEmail;
}
