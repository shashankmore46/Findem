package org.example.ingestor.cleaner;

import static org.example.constants.Constants.REGION_MAP;

public class RegionCleaner implements FieldCleaner {
    @Override
    public Object clean(Object value) {
        String key = value.toString().trim().toLowerCase();
        return REGION_MAP.getOrDefault(key, value.toString().trim());
    }
}

