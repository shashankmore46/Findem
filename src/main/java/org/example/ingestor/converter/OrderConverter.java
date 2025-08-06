package org.example.ingestor.converter;

import org.example.model.Order;
import org.example.model.RawOrder;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.example.constants.Constants.STANDARD_DATE_FORMAT;

public class OrderConverter implements Converter<RawOrder, Order> {
    @Override
    public Order convert(RawOrder from) {
        int quantity = Integer.parseInt(from.getQuantity());
        float unitPrice = Float.parseFloat(from.getUnitPrice());
        float discount = Float.parseFloat(from.getDiscountPercent());
        float revenue = (float) (quantity * unitPrice * (1.0 - discount));
        return new Order(
                from.getOrderId(),
                from.getProductName(),
                from.getCategory(),
                quantity,
                unitPrice,
                discount,
                from.getRegion(),
                LocalDate.parse(from.getSaleDate(), DateTimeFormatter.ofPattern(STANDARD_DATE_FORMAT)),
                from.getCustomerEmail(),
                revenue
        );
    }
}
