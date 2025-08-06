package org.example.utils;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class RawOrderDataGenerator {

    private static final String[] PRODUCT_NAMES = {"TV", "T.V.", "Television", "Laptop", "Lap top", "Fridge", "Refrigerator"};
    private static final String[] CATEGORIES = {"electronics", "home appliance", "elec", "appliance", "gadget"};
    private static final String[] REGIONS = {"North", "nort", "South", "suth", "East", "Eest", "West", "Wst"};
    private static final String[] EMAIL_DOMAINS = {"example.com", "test.org", "mail.com", "sample.net"};
    private static final DateTimeFormatter[] DATE_FORMATS = {
            DateTimeFormatter.ofPattern("yyyy-MM-dd"),
            DateTimeFormatter.ofPattern("dd/MM/yyyy"),
            DateTimeFormatter.ofPattern("MM-dd-yyyy")
    };
    private static final Random RANDOM = new Random();

    public static void generateRawOrdersCsv(String filePath, int count) throws IOException {
        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write(String.join("|",
                    "order_id",
                    "product_name",
                    "category",
                    "quantity",
                    "unit_price",
                    "discount_percent",
                    "region",
                    "sale_date",
                    "customer_email"
            ));
            writer.write("\n");
            for (int i = 0; i < count; i++) {
                String orderId = "ORD" + (10000 + RANDOM.nextInt(90000));
                String productName = PRODUCT_NAMES[RANDOM.nextInt(PRODUCT_NAMES.length)];
                String category = CATEGORIES[RANDOM.nextInt(CATEGORIES.length)];
                String quantity = String.valueOf(RANDOM.nextInt(20) - 5); // -5 to 14, can be negative or zero
                float unitPrice = RANDOM.nextFloat() * 1000f;
                float discountPercent = RANDOM.nextFloat() * 1.2f; // can be >1.0 (dirty)
                String region = REGIONS[RANDOM.nextInt(REGIONS.length)];

                // Randomly choose to make sale_date null or in various formats
                String saleDate = null;
                if (RANDOM.nextDouble() > 0.15) { // 85% chance to have a date
                    LocalDate date = LocalDate.now().minusDays(RANDOM.nextInt(1000));
                    DateTimeFormatter fmt = DATE_FORMATS[RANDOM.nextInt(DATE_FORMATS.length)];
                    saleDate = date.format(fmt);
                }

                // Randomly choose to make customer_email null
                String customerEmail = null;
                if (RANDOM.nextDouble() > 0.2) { // 80% chance to have email
                    customerEmail = "user" + RANDOM.nextInt(10000) + "@" + EMAIL_DOMAINS[RANDOM.nextInt(EMAIL_DOMAINS.length)];
                }

                // Write as pipe-separated CSV
                writer.write(String.join("|",
                        orderId,
                        productName,
                        category,
                        quantity,
                        String.valueOf(unitPrice),
                        String.valueOf(discountPercent),
                        region,
                        saleDate == null ? "" : saleDate,
                        customerEmail == null ? "" : customerEmail
                ));
                writer.write("\n");
            }
        }
    }
}
