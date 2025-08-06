package org.example.ingestor.cleaner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import static org.example.constants.Constants.STANDARD_DATE_FORMAT;
import static org.example.constants.Constants.SUPPORTED_DATE_FORMATS;

public class DateCleaner implements FieldCleaner {
    @Override
    public Object clean(Object value) {
        String dateStr = (String) value;
        Date parsedDate = null;
        for (String format : SUPPORTED_DATE_FORMATS) {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            sdf.setLenient(false);
            try {
                parsedDate = sdf.parse(dateStr);
                break;
            } catch (ParseException ignored) {
            }
        }
        LocalDate localDate = parsedDate.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
        return localDate.format(DateTimeFormatter.ofPattern(STANDARD_DATE_FORMAT));
    }
}
