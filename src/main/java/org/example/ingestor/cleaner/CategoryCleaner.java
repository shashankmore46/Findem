package org.example.ingestor.cleaner;

import static org.example.constants.Constants.CATEGORY_MAP;

public class CategoryCleaner implements FieldCleaner {
    @Override
    public Object clean(Object value) {
        String key = value.toString().trim().toLowerCase();
        return CATEGORY_MAP.getOrDefault(key, value.toString().trim());
    }
}
