package org.example.constants;

import java.util.Arrays;
import java.util.List;

public final class Constants {
    public static final String DB_URL = "jdbc:duckdb:./mydb.duckdb";
    public static final String DIRTY_ORDERS = "dirty_orders";
    public static final String ORDERS = "orders";
    public static final List<String> SUPPORTED_DATE_FORMATS = Arrays.asList(
            "yyyy-MM-dd",
            "dd/MM/yyyy",
            "MM-dd-yyyy"
    );
    public static final String STANDARD_DATE_FORMAT = "yyyy-MM-dd";

    private Constants() {
    }
}
