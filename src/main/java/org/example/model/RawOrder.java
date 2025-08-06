package org.example.model;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.ingestor.cleaner.*;
import org.example.ingestor.validator.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class RawOrder {
    @Validator(handler = StringValidator.class)
    private String orderId;

    @Validator(handler = StringValidator.class)
    @Cleaner(handler = ProductCleaner.class)
    private String productName;

    @Validator(handler = StringValidator.class)
    @Cleaner(handler = CategoryCleaner.class)
    private String category;

    @Validator(handler = QuantityValidator.class)
    private String quantity;

    @Validator(handler = UnitPriceValidator.class)
    private String unitPrice;

    @Validator(handler = DiscountPercentValidator.class)
    private String discountPercent;

    @Validator(handler = StringValidator.class)
    @Cleaner(handler = RegionCleaner.class)
    private String region;

    @Validator(handler = DateValidator.class)
    @Cleaner(handler = DateCleaner.class)
    private String saleDate;

    @Validator(handler = EmailValidator.class)
    private String customerEmail;
}
