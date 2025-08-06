package org.example.utils;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVWriter;
import org.example.model.RawOrder;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;

import static org.example.constants.Constants.DB_URL;

public class CsvUtils {
    private static final CSVParser parser = new CSVParserBuilder().withSeparator('|').build();

    public static RawOrder parseRawOrder(String csvLine) {
        try {
            String[] fields = parser.parseLine(csvLine);
            String orderId = fields[0];
            String productName = fields[1];
            String category = fields[2];
            String quantity = fields[3];
            String unitPrice = fields[4];
            String discountPercent = fields[5];
            String region = fields[6];
            String saleDate = fields[7];
            String customerEmail = fields[8];
            return new RawOrder(
                    orderId,
                    productName,
                    category,
                    quantity,
                    unitPrice,
                    discountPercent,
                    region,
                    saleDate,
                    customerEmail
            );
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse CSV line: " + csvLine, e);
        }
    }

    public static void dumpTableToCSV(String tableName, String filePath) {
        String sql = "SELECT * FROM " + tableName;
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql);
             CSVWriter writer = new CSVWriter(
                     new FileWriter(filePath),
                     '|',
                     CSVWriter.NO_QUOTE_CHARACTER,
                     CSVWriter.DEFAULT_ESCAPE_CHARACTER,
                     CSVWriter.DEFAULT_LINE_END
             )) {
            ResultSetMetaData meta = rs.getMetaData();
            int colCount = meta.getColumnCount();
            String[] header = new String[colCount];
            for (int i = 0; i < colCount; i++) {
                header[i] = meta.getColumnName(i + 1);
            }
            writer.writeNext(header);
            while (rs.next()) {
                String[] row = new String[colCount];
                for (int i = 0; i < colCount; i++) {
                    Object obj = rs.getObject(i + 1);
                    row[i] = (obj == null) ? "" : obj.toString();
                }
                writer.writeNext(row);
            }
            writer.flush();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
