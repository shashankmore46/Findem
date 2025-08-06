package org.example.ingestor.cleaner;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.example.constants.Constants.STANDARD_DATE_FORMAT;

public class DateCleaner implements FieldCleaner {
    @Override
    public Object clean(Object value) {
        String dateStr = (String) value;
        return LocalDate.parse(dateStr, DateTimeFormatter.ofPattern(STANDARD_DATE_FORMAT));
    }
}
