package org.example;

import org.example.ingestor.Reader;
import org.example.ingestor.Writer;
import org.example.utils.CsvUtils;
import org.example.utils.RawOrderDataGenerator;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.example.constants.Constants.*;

public class App {
    public static void main(String[] args) throws Exception {
        String cmd = args[0];
        switch (cmd) {
            case "generate": {
                String filePath = args[1];
                int count = Integer.parseInt(args[2]);
                RawOrderDataGenerator.generateRawOrdersCsv(filePath, count);
                break;
            }
            case "ingest": {
                String filePath = args[1];
                int chunkSize = Integer.parseInt(args[2]);
                try (Connection conn = DriverManager.getConnection(DB_URL);
                     Statement stmt = conn.createStatement()) {
                    stmt.execute("DROP TABLE IF EXISTS dirty_orders;");
                    stmt.execute("CREATE TABLE dirty_orders (\n" +
                            "    order_id VARCHAR,\n" +
                            "    product_name VARCHAR,\n" +
                            "    category VARCHAR,\n" +
                            "    quantity VARCHAR,\n" +
                            "    unit_price VARCHAR,\n" +
                            "    discount_percent VARCHAR,\n" +
                            "    region VARCHAR,\n" +
                            "    sale_date VARCHAR,\n" +
                            "    customer_email VARCHAR\n" +
                            ");");
                    stmt.execute("DROP TABLE IF EXISTS orders;");
                    stmt.execute("CREATE TABLE orders (\n" +
                            "    order_id VARCHAR PRIMARY KEY,\n" +
                            "    product_name VARCHAR NOT NULL,\n" +
                            "    category VARCHAR NOT NULL,\n" +
                            "    quantity INTEGER NOT NULL,\n" +
                            "    unit_price FLOAT NOT NULL,\n" +
                            "    discount_percent FLOAT NOT NULL,\n" +
                            "    region VARCHAR NOT NULL,\n" +
                            "    sale_date DATE NOT NULL,\n" +
                            "    customer_email VARCHAR NOT NULL,\n" +
                            "    revenue FLOAT NOT NULL\n" +
                            ");");
                }
                ExecutorService workerPool = Executors.newFixedThreadPool(10);
                Writer writer = new Writer();
                writer.createTableWriter(DIRTY_ORDERS);
                writer.createTableWriter(ORDERS);
                new Reader(filePath, workerPool, writer, chunkSize).start();
                workerPool.awaitTermination(15, TimeUnit.MINUTES);
                break;
            }
            case "analyze": {
                try (Connection conn = DriverManager.getConnection(DB_URL);
                     Statement stmt = conn.createStatement()) {
                    stmt.execute("DROP TABLE IF EXISTS monthly_sales_summary;");
                    stmt.execute("CREATE TABLE monthly_sales_summary AS\n" +
                            "SELECT \n" +
                            "    DATE_TRUNC('month', sale_date) AS sale_month,\n" +
                            "    SUM(revenue)          AS total_revenue,\n" +
                            "    SUM(quantity)         AS total_quantity,\n" +
                            "    AVG(discount_percent) AS avg_discount\n" +
                            "FROM orders\n" +
                            "GROUP BY sale_month\n" +
                            "ORDER BY sale_month;");
                    stmt.execute("DROP TABLE IF EXISTS top_products_by_revenue;");
                    stmt.execute("CREATE TABLE top_products_by_revenue AS\n" +
                            "SELECT \n" +
                            "    product_name,\n" +
                            "    SUM(revenue)  AS total_revenue,\n" +
                            "    SUM(quantity) AS total_units\n" +
                            "FROM orders\n" +
                            "GROUP BY product_name\n" +
                            "ORDER BY total_revenue DESC\n" +
                            "LIMIT 10;");
                    stmt.execute("DROP TABLE IF EXISTS top_products_by_units;");
                    stmt.execute("CREATE TABLE top_products_by_units AS\n" +
                            "SELECT \n" +
                            "    product_name,\n" +
                            "    SUM(revenue)  AS total_revenue,\n" +
                            "    SUM(quantity) AS total_units\n" +
                            "FROM orders\n" +
                            "GROUP BY product_name\n" +
                            "ORDER BY total_units DESC\n" +
                            "LIMIT 10;");
                    stmt.execute("DROP TABLE IF EXISTS region_wise_performance;");
                    stmt.execute("CREATE TABLE region_wise_performance AS\n" +
                            "SELECT\n" +
                            "    region,\n" +
                            "    SUM(revenue)          AS total_revenue,\n" +
                            "    SUM(quantity)         AS total_units,\n" +
                            "    AVG(discount_percent) AS avg_discount\n" +
                            "FROM orders\n" +
                            "GROUP BY region\n" +
                            "ORDER BY total_revenue DESC;");
                    stmt.execute("DROP TABLE IF EXISTS category_discount_map;");
                    stmt.execute("CREATE TABLE category_discount_map AS\n" +
                            "SELECT\n" +
                            "    category,\n" +
                            "    AVG(discount_percent) AS avg_discount,\n" +
                            "    COUNT(*)              AS order_count\n" +
                            "FROM orders\n" +
                            "GROUP BY category\n" +
                            "ORDER BY avg_discount DESC;");
                    stmt.execute("DROP TABLE IF EXISTS anomaly_records;");
                    stmt.execute("CREATE TABLE anomaly_records AS\n" +
                            "SELECT *\n" +
                            "FROM orders\n" +
                            "ORDER BY revenue DESC\n" +
                            "LIMIT 5;");

                }
                CsvUtils.dumpTableToCSV("monthly_sales_summary", "monthly_sales_summary.csv");
                CsvUtils.dumpTableToCSV("top_products_by_revenue", "top_products_by_revenue.csv");
                CsvUtils.dumpTableToCSV("top_products_by_units", "top_products_by_units.csv");
                CsvUtils.dumpTableToCSV("region_wise_performance", "region_wise_performance.csv");
                CsvUtils.dumpTableToCSV("category_discount_map", "category_discount_map.csv");
                CsvUtils.dumpTableToCSV("anomaly_records", "anomaly_records.csv");
                break;
            }
            case "dashboard": {
                System.out.println("Dashboard feature is under development.");
                break;
            }
            default: {
                throw new IllegalArgumentException("Unknown command: " + cmd);
            }
        }
    }
}
