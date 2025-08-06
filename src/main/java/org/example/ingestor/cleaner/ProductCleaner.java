package org.example.ingestor.cleaner;

import static org.example.constants.Constants.PRODUCT_NAME_MAP;

public class ProductCleaner implements FieldCleaner {
    @Override
    public Object clean(Object value) {
        String key = value.toString().trim().toLowerCase();
        return PRODUCT_NAME_MAP.getOrDefault(key, value.toString().trim());
    }
}
