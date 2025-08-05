package org.example.ingestor.validator;

import org.example.exception.ValidationException;

public interface FieldValidator {
    void validate(Object value) throws ValidationException;
}