package org.example.ingestor.validator;

import org.example.exception.ValidationException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.example.constants.Constants.SUPPORTED_DATE_FORMATS;

public class DateValidator implements FieldValidator {
    @Override
    public void validate(Object value) throws ValidationException {
        if (value == null) {
            throw new ValidationException("Date value cannot be null");
        }
        if (!(value instanceof String)) {
            throw new ValidationException("Date value must be a string");
        }
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
        if (parsedDate == null) {
            throw new ValidationException("Invalid date format. Supported formats: " + SUPPORTED_DATE_FORMATS);
        }
    }
}
