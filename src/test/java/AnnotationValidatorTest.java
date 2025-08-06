import org.example.ingestor.validator.AnnotationValidator;
import org.example.model.RawOrder;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AnnotationValidatorTest {
    @Test
    void dateValidatorCatchesInvalidDate() {
        RawOrder rawOrder = new RawOrder(
                "ORD12345",
                "T.V.",
                "elec",
                "3",
                "1299.99",
                "0.10",
                "nort",
                "12/09/2023",
                "user77@example.com"
        );
        rawOrder.setSaleDate("not-a-date");
        assertThrows(Exception.class, () -> AnnotationValidator.validateFields(rawOrder));
    }

    @Test
    void quantityValidatorCatchesNegative() {
        RawOrder rawOrder = new RawOrder(
                "ORD12345",
                "T.V.",
                "elec",
                "3",
                "1299.99",
                "0.10",
                "nort",
                "12/09/2023",
                "user77@example.com"
        );
        rawOrder.setQuantity("-5");
        assertThrows(Exception.class, () -> AnnotationValidator.validateFields(rawOrder));
    }

    @Test
    void notNullValidatorCatchesNullProduct() {
        RawOrder rawOrder = new RawOrder(
                "ORD12345",
                "T.V.",
                "elec",
                "3",
                "1299.99",
                "0.10",
                "nort",
                "12/09/2023",
                "user77@example.com"
        );
        rawOrder.setProductName(null);
        assertThrows(Exception.class, () -> AnnotationValidator.validateFields(rawOrder));
    }

    @Test
    void quantityValidatorValid() {
        RawOrder rawOrder = new RawOrder(
                "ORD12345",
                "T.V.",
                "elec",
                "3",
                "1299.99",
                "0.10",
                "nort",
                "12/09/2023",
                "user77@example.com"
        );
        rawOrder.setQuantity("5");
        assertDoesNotThrow(() -> AnnotationValidator.validateFields(rawOrder));
    }
}

