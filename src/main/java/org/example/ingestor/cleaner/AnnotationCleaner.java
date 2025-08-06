package org.example.ingestor.cleaner;

import java.lang.reflect.Field;

public class AnnotationCleaner {
    public static void cleanFields(Object obj) throws Exception {
        Class<?> clazz = obj.getClass();
        for (Field field : clazz.getDeclaredFields()) {
            Cleaner cleaner = field.getAnnotation(Cleaner.class);
            if (cleaner != null) {
                field.setAccessible(true);
                Object value = field.get(obj);
                if (value instanceof String) {
                    FieldCleaner handler = cleaner.handler().getDeclaredConstructor().newInstance();
                    String cleaned = handler.clean(value).toString();
                    field.set(obj, cleaned);
                }
            }
        }
    }
}
