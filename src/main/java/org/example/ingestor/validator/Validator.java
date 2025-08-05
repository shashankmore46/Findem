package org.example.ingestor.validator;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Validator {
    Class<? extends FieldValidator> handler();
}