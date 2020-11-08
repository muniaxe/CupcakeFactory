package olskercupcakes.api;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UtilsTest {
    Utils utils = new Utils();

    @Test
    void formattedPriceTest() {
        String formattedPrice = utils.formattedPrice(200000);
        //We expect this to follow Danish Locale logic.
        //So 200000 cents would be 2.000,00 kr

        assertEquals("2.000,00", formattedPrice);
    }

}