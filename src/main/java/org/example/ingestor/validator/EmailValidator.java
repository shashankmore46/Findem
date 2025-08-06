package org.example.ingestor.validator;

import org.example.exception.ValidationException;

public class EmailValidator implements FieldValidator {
    @Override
    public void validate(Object value) throws ValidationException {
        if (value == null) {
            throw new ValidationException("Email value cannot be null");
        }
        if (!(value instanceof String)) {
            throw new ValidationException("Email value must be a string");
        }
        String email = (String) value;
        if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            throw new ValidationException("Invalid email format: " + email);
        }
    }
}
