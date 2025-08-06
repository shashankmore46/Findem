package org.example.ingestor.validator;

import org.example.exception.ValidationException;

public class UnitPriceValidator implements FieldValidator {
    @Override
    public void validate(Object value) throws ValidationException {
        float v = Float.parseFloat(value.toString());
        if (v < 0) {
            throw new ValidationException("Unit price must non negative.");
        }
    }
}
