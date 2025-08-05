package org.example.ingestor.validator;

import java.lang.reflect.Field;

public class AnnotationValidator {
    public static void validateFields(Object obj) throws Exception {
        for (Field field : obj.getClass().getDeclaredFields()) {
            if (field.isAnnotationPresent(Validator.class)) {
                Validator v = field.getAnnotation(Validator.class);
                field.setAccessible(true);
                FieldValidator handler = v.handler().getDeclaredConstructor().newInstance();
                handler.validate(field.get(obj));
            }
        }
    }
}
