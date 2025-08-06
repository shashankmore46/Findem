import org.example.ingestor.cleaner.AnnotationCleaner;
import org.example.model.RawOrder;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AnnotationCleanerTest {
    @Test
    void productNameCleanerFixesTypos() throws Exception {
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
        AnnotationCleaner.cleanFields(rawOrder);
        assertEquals("TV", rawOrder.getProductName());
    }

    @Test
    void regionCleanerFixesVariant() throws Exception {
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
        rawOrder.setRegion("nort");
        AnnotationCleaner.cleanFields(rawOrder);
        assertEquals("North", rawOrder.getRegion());
    }

    @Test
    void dateFormatCleanerParsesDate() throws Exception {
        RawOrder rawOrder = new RawOrder(
                "ORD12345",
                "T.V.",
                "elec",
                "3",
                "1299.99",
                "0.10",
                "nort",
                "12/05/2022",
                "user77@example.com"
        );
        AnnotationCleaner.cleanFields(rawOrder);
        assertEquals("2022-05-12", rawOrder.getSaleDate());
    }
}
