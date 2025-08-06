package org.example.ingestor.validator;

import org.example.exception.ValidationException;

public class DiscountPercentValidator implements FieldValidator {
    @Override
    public void validate(Object value) throws ValidationException {
        float v = Float.parseFloat(value.toString());
        if (v < 0 || v > 1) {
            throw new ValidationException("Discount percent must be within range [0,1].");
        }
    }
}
