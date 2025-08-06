package org.example.ingestor.validator;

import org.example.exception.ValidationException;

public class StringValidator implements FieldValidator {
    @Override
    public void validate(Object value) throws ValidationException {
        if (!(value instanceof String)) {
            throw new ValidationException("Value must be a non-null string.");
        }
    }
}
