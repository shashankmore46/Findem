package org.example.constants;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

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
    public static final String[] PRODUCT_NAMES = {"TV", "T.V.", "Television", "Laptop", "Lap top", "Fridge", "Refrigerator"};
    public static final String[] CATEGORIES = {"electronics", "home appliance", "elec", "appliance", "gadget"};
    public static final String[] REGIONS = {"North", "nort", "South", "suth", "East", "Eest", "West", "Wst"};
    public static final String[] EMAIL_DOMAINS = {"example.com", "test.org", "mail.com", "sample.net"};
    public static final Map<String, String> PRODUCT_NAME_MAP = Map.of(
            "tv", "TV",
            "t.v.", "TV",
            "television", "TV",
            "laptop", "Laptop",
            "lap top", "Laptop",
            "fridge", "Fridge",
            "refrigerator", "Fridge"
    );
    public static final Map<String, String> REGION_MAP = Map.of(
            "north", "North",
            "nort", "North",
            "south", "South",
            "suth", "South",
            "east", "East",
            "eest", "East",
            "west", "West",
            "wst", "West"
    );
    public static final Map<String, String> CATEGORY_MAP = Map.of(
            "electronics", "Electronics",
            "elec",        "Electronics",
            "gadget",      "Electronics",
            "home appliance", "Home Appliance",
            "appliance",      "Home Appliance"
    );


    private Constants() {
    }
}
