package org.example.ingestor;

import org.example.ingestor.converter.OrderConverter;
import org.example.ingestor.validator.AnnotationValidator;
import org.example.model.Order;
import org.example.model.RawOrder;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static org.example.constants.Constants.*;

public class WorkerTask implements Runnable {
    private final List<RawOrder> chunk;
    private final OrderConverter orderConverter;
    private final Writer writer;

    public WorkerTask(List<RawOrder> chunk, Writer writer) {
        this.chunk = chunk;
        this.orderConverter = new OrderConverter();
        this.writer = writer;
    }

    @Override
    public void run() {
        List<RawOrder> dirtyOrders = new ArrayList<>();
        List<Order> orders = new ArrayList<>();
        for (RawOrder rawOrder : chunk) {
            try {
                AnnotationValidator.validateFields(rawOrder);
                orders.add(orderConverter.convert(rawOrder));
            } catch (Exception ex) {
                dirtyOrders.add(rawOrder);
            }
        }
        if (!dirtyOrders.isEmpty()) {
            writer.write(DIRTY_ORDERS, () -> {
                try (Connection conn = DriverManager.getConnection(DB_URL)) {
                    String dirtySql = "INSERT INTO dirty_orders " +
                            "(order_id, product_name, category, quantity, unit_price, discount_percent, region, sale_date, customer_email) " +
                            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
                    PreparedStatement ps = conn.prepareStatement(dirtySql);
                    for (RawOrder rawOrder : dirtyOrders) {
                        ps.setString(1, rawOrder.getOrderId());
                        ps.setString(2, rawOrder.getProductName());
                        ps.setString(3, rawOrder.getCategory());
                        ps.setString(4, rawOrder.getQuantity());
                        ps.setString(5, rawOrder.getUnitPrice());
                        ps.setString(6, rawOrder.getDiscountPercent());
                        ps.setString(7, rawOrder.getRegion());
                        ps.setString(8, rawOrder.getSaleDate());
                        ps.setString(9, rawOrder.getCustomerEmail());
                        ps.addBatch();
                    }
                    ps.executeBatch();
                    System.out.println("Inserted " + dirtyOrders.size() + " dirty orders into the database.");
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            });
        }
        if (!orders.isEmpty()) {
            writer.write(ORDERS, () -> {
                try (Connection conn = DriverManager.getConnection(DB_URL)) {
                    String orderSql = "INSERT INTO orders " +
                            "(order_id, product_name, category, quantity, unit_price, discount_percent, region, sale_date, customer_email, revenue) " +
                            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)" + "ON CONFLICT(order_id) DO UPDATE SET " +
                            "product_name=excluded.product_name, " +
                            "category=excluded.category, " +
                            "quantity=excluded.quantity, " +
                            "unit_price=excluded.unit_price, " +
                            "discount_percent=excluded.discount_percent, " +
                            "region=excluded.region, " +
                            "sale_date=excluded.sale_date, " +
                            "customer_email=excluded.customer_email, " +
                            "revenue=excluded.revenue";
                    PreparedStatement ps = conn.prepareStatement(orderSql);
                    for (Order order : orders) {
                        ps.setString(1, order.getOrderId());
                        ps.setString(2, order.getProductName());
                        ps.setString(3, order.getCategory());
                        ps.setInt(4, order.getQuantity());
                        ps.setFloat(5, order.getUnitPrice());
                        ps.setFloat(6, order.getDiscountPercent());
                        ps.setString(7, order.getRegion());
                        ps.setDate(8, Date.valueOf(order.getSaleDate()));
                        ps.setString(9, order.getCustomerEmail());
                        ps.setFloat(10, order.getRevenue());
                        ps.addBatch();
                    }
                    ps.executeBatch();
                    System.out.println("Inserted " + orders.size() + " orders into the database.");
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            });
        }
    }
}