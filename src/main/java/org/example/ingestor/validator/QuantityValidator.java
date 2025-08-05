package org.example.ingestor.validator;

import org.example.exception.ValidationException;

public class QuantityValidator implements FieldValidator {
    @Override
    public void validate(Object value) throws ValidationException {
//        if (!(value instanceof Integer)) throw new ValidationException("Quantity is not an integer.");
        int v = Integer.parseInt(value.toString());
        if (v < 0) {
            throw new ValidationException("Quantity must non negative.");
        }
    }
}
