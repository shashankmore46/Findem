package org.example;

import org.example.ingestor.validator.AnnotationValidator;
import org.example.model.RawOrder;

public class Main {
    public static void main(String[] args) throws Exception {
        RawOrder rawOrder = new RawOrder();
        rawOrder.setQuantity("0");
        AnnotationValidator.validateFields(rawOrder);
    }
}