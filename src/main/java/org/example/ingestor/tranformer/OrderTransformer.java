package org.example.ingestor.tranformer;

import org.example.model.Order;
import org.example.model.RawOrder;

public class OrderTransformer implements Transform<RawOrder, Order> {
    @Override
    public Order transform(RawOrder from) {
        int quantity = Integer.parseInt(from.getQuantity());
        float unitPrice = from.getUnitPrice();
        float discount = from.getDiscountPercent();
        float revenue = (float) (quantity * unitPrice * (1.0 - discount));
        return new Order(
                from.getOrderId(),
                from.getProductName(),
                from.getCategory(),
                quantity,
                unitPrice,
                discount,
                from.getRegion(),
                from.getSaleDate(),
                from.getCustomerEmail(),
                revenue
        );
    }
}
